package com.common.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
@Configuration
@RequiredArgsConstructor
@Primary
public class DatabaseConfig {
    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://172.20.29.144:3306/qlynhansu")
                .url("jdbc:mysql://localhost:3306/ecommerce")
                .username("root")
                .password("123456aA@");
        return dataSourceBuilder.build();
    }
}