package net.zero.cloud.config;

import com.alibaba.fastjson.JSONArray;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import lombok.extern.slf4j.Slf4j;
import org.cfg4j.source.context.propertiesprovider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
@RefreshScope
@Slf4j
public class ConsulConfiguration {

    @Autowired
    private ConsulClient consulClient;
    /**
     * 是否用本地配置覆盖consul远程配置，默认不覆盖, 覆盖: true / 不覆盖: false
     */
    @Value("${spring.cloud.consul.config.cover: false}")
    private Boolean cover;
    /**
     * key所在的目录前缀，格式为：config/应用名称/
     */
    //@Value("#{'${spring.cloud.consul.config.prefix}/'.concat('${spring.cloud.consul.config.name}/')}")
    //private String keyPrefix;
    @Value("${spring.cloud.consul.config.prefix}")
    private String consulPath;
    /**
     * 加载配置信息到consul中
     *
     * @param key     配置的key
     * @param value   配置的值
     * @param keyList 在consul中已存在的配置信息key集合
     */
    private void visitProps(String key, Object value, List<String> keyList,String appName) {
        if (value.getClass() == String.class || value.getClass() == JSONArray.class) {
            // 覆盖已有配置
            if (cover) {
                this.setKVValue(key, value.toString(),this.consulPath+"/"+appName+"/");
            } else {
                if (keyList != null && !keyList.contains(key)) {
                    this.setKVValue(key, value.toString(),this.consulPath+"/"+appName+"/");
                }
            }
        } else if (value.getClass() == LinkedHashMap.class) {
            Map<String, Object> map = (LinkedHashMap) value;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                visitProps(key + "." + entry.getKey(), entry.getValue(), keyList,appName);
            }
        } else if (value.getClass() == HashMap.class) {
            Map<String, Object> map = (HashMap) value;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                visitProps(key + "." + entry.getKey(), entry.getValue(), keyList,appName);
            }
        }
    }


    /**
     * 封装配置信息到map中
     *
     * @param map 要封装的配置信息
     * @return 配置信息map
     */
    private Map<String, Object> formatMap(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>(16);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue().getClass() == LinkedHashMap.class) {
                Map<String, Object> subMap = formatMap((Map<String, Object>) entry.getValue());
                newMap.put(entry.getKey(), subMap);
            } else if (entry.getValue().getClass() == ArrayList.class) {
                JSONArray jsonArray = new JSONArray((ArrayList) entry.getValue());
                newMap.put(entry.getKey(), jsonArray);
            } else {
                newMap.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return newMap;
    }

    /**
     * 解析yml配置
     *
     * @param inputStream 要解析的yml文件输入流
     * @return 解析结果
     */
    private Map<String, Object> paserYml(InputStream inputStream) {
        Map<String, Object> newMap = new HashMap<>(16);
        try {
            Yaml yaml = new Yaml();
            Map map =  yaml.load(inputStream);
            newMap = formatMap(map);
        } catch (Exception e) {
            log.warn("解析Yml文件出现异常!");
        }
        return newMap;
    }

    /**
     * 启动时加载application.yml配置文件信息到consul配置中心
     * 加载到Consul的文件在ClassPathResource中指定
     */
    public void upload(String appName) {
        Map<String, Object> props = getProperties(appName,null);
        List<String> keyList = this.getKVKeysOnly(this.consulPath+"/"+appName);
        log.info("Found keys : {}", keyList);
        for (Map.Entry<String, Object> prop : props.entrySet()) {
            //判断有spring.profiles.active则读取对应文件下的配置
            if (prop.getKey().equals("spring.profiles.active")) {
                Map<String, Object> props2 = getProperties(appName,(String) prop.getValue());
                for (Map.Entry<String, Object> prop2 : props2.entrySet()) {
                    visitProps(prop2.getKey(), prop2.getValue(), keyList,appName);
                }
                continue;
            }
            visitProps(prop.getKey(), prop.getValue(), keyList,appName);
        }
    }
/*
    @PostConstruct
    private void init(){
        upload("zerocloud-auth");
    }
*/
    /**
     * 读取配置文件中的内容
     *
     * @param fixed
     * @return
     */
    private Map<String, Object> getProperties(String appName,String fixed) {
        PropertiesProviderSelector propertiesProviderSelector = new PropertiesProviderSelector(
                new PropertyBasedPropertiesProvider(), new YamlBasedPropertiesProvider(), new JsonBasedPropertiesProvider()
        );
        ClassPathResource resource;
        if (fixed != null && !fixed.isEmpty()) {
            resource = new ClassPathResource(appName+"/application-" + fixed + ".properties");
        } else {
            resource = new ClassPathResource(appName+ "/application.properties");
        }
        String fileName = resource.getFilename();
        String path = null;
        Map<String, Object> props = new HashMap<>(16);
        try (InputStream input = resource.getInputStream()) {
            log.info("Found config file: " + resource.getFilename() + " in context " + resource.getURL().getPath());
            path = resource.getURL().getPath();
            if (fileName.endsWith(".properties")) {
                PropertiesProvider provider = propertiesProviderSelector.getProvider(fileName);
                props = (Map) provider.getProperties(input);

            } else if (fileName.endsWith(".yml")) {
                props = paserYml(resource.getInputStream());
            }
        } catch (IOException e) {
            log.warn("Unable to load properties from file: {},message: {} ", path, e.getMessage());
        }
        return props;
    }
    /**
     * 将应用的配置信息保存到consul中
     *
     * @param kvValue 封装的配置信息的map对象
     */

    public void setKVValue(Map<String, String> kvValue,String keyPrefix) {
        for (Map.Entry<String, String> kv : kvValue.entrySet()) {
            try {
                this.consulClient.setKVValue(keyPrefix + kv.getKey(), kv.getValue());
            } catch (Exception e) {
                log.warn("SetKVValue exception: {},kvValue: {}", e.getMessage(), kvValue);
            }
        }
    }

    public void setKVValue(String key, String value,String keyPrefix) {
        try {
            this.consulClient.setKVValue(keyPrefix + key, value);
        } catch (Exception e) {
            log.warn("SetKVValue exception: {},key: {},value: {}", e.getMessage(), key, value);
        }
    }

    /**
     * 获取应用配置的所有key-value信息
     *
     * @param keyPrefix key所在的目录前缀，格式为：config/应用名称/
     * @return 应用配置的所有key-value信息
     */

    public Map<String, String> getKVValues(String keyPrefix) {
        Map<String, String> map = new HashMap<>(16);

        try {
            Response<List<GetValue>> response = this.consulClient.getKVValues(keyPrefix);
            if (response != null) {
                for (GetValue getValue : response.getValue()) {
                    int index = getValue.getKey().lastIndexOf("/") + 1;
                    String key = getValue.getKey().substring(index);
                    String value = getValue.getDecodedValue();
                    map.put(key, value);
                }
            }
            return map;
        } catch (Exception e) {
            log.warn("GetKVValues exception: {},keyPrefix: {}", e.getMessage(), keyPrefix);
        }
        return null;
    }


//    public Map<String, String> getKVValues() {
//        return this.getKVValues(keyPrefix);
//    }

    /**
     * 获取应用配置的所有key信息
     *
     * @param keyPrefix key所在的目录前缀，格式为：config/应用名称/
     * @return 应用配置的所有key信息
     */

    public List<String> getKVKeysOnly(String keyPrefix) {
        List<String> list = new ArrayList<>();
        try {
            Response<List<String>> response = this.consulClient.getKVKeysOnly(keyPrefix);

            if (response.getValue() != null) {
                for (String key : response.getValue()) {
                    int index = key.lastIndexOf("/") + 1;
                    String temp = key.substring(index);
                    list.add(temp);
                }
            }
            return list;
        } catch (Exception e) {
            log.warn("GetKVKeysOnly exception: {},keyPrefix: {}", e.getMessage(), keyPrefix);
        }
        return null;
    }

//    public List<String> getKVKeysOnly(String keyPrefix) {
//        return this.getKVKeysOnly(keyPrefix);
//    }
}