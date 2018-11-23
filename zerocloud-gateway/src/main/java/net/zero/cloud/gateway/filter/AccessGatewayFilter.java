package net.zero.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.zero.cloud.common.web.ResponseDto;
import net.zero.cloud.gateway.admin.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class AccessGatewayFilter implements GlobalFilter {
    @Autowired
    private AdminService adminService;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain) {
        log.info("check token and user permission....");
        LinkedHashSet requiredAttribute = serverWebExchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        ServerHttpRequest request = serverWebExchange.getRequest();

        String requestUri = request.getPath().pathWithinApplication().value();
        log.debug(requestUri);
        final String method = request.getMethod().toString();
        log.debug(method);
        ServerHttpRequest.Builder mutate = request.mutate();
        // 申请客户端密钥头
        //mutate.header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        ServerHttpRequest build = mutate.build();
        // 不进行拦截的地址
        List<String> accessDto = adminService.getAccessBean(requestUri);
        log.debug(accessDto.toString());

        return gatewayFilterChain.filter(serverWebExchange.mutate().request(build).build());

    }

    /**
     * 网关抛异常
     *
     * @param body
     */

    private Mono<Void> getVoidMono(ServerWebExchange serverWebExchange, ResponseDto body) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = JSONObject.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }


    /**
     * 网关抛异常
     *
     * @param body
     * @param code
     */
    private Mono<Void> setFailedRequest(ServerWebExchange serverWebExchange, String body, int code) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.OK);
        return serverWebExchange.getResponse().setComplete();
    }

}