package net.zero.cloud.auth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"net.zero.cloud.auth.mapper"})
public class MyBatisConfig {
}