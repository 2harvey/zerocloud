package net.zero.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableAutoConfiguration
@Slf4j
public class ApplicationConfig  implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
    @Override
    public void run(ApplicationArguments args) {
        log.info("CONFIG...");
    }
}
