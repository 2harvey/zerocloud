package net.zero.cloud.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableScheduling
//@EnableAutoConfiguration
@EnableFeignClients({"net.zero.cloud.gateway.admin"})
@Slf4j
public class ApplicationZerocloudGateway implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationZerocloudGateway.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("GATEWAY 开始工作。");
        //config.upload("zerocloud-auth");
    }
}
