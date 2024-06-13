package com.legday.spring02.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.legday.spring02.mapper")
public class MyBatisConfig {

}
