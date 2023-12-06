package com.simol.batch.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ApplingDataSourceConfig {
    @Value("${spring.datasource.hikari.appling.jdbc-url}")
    private String url;

    @Value("${spring.datasource.hikari.appling.username}")
    private String username;

    @Value("${spring.datasource.hikari.appling.password}")
    private String password;

    @Value("${spring.datasource.hikari.appling.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource applingDateSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean
    public PlatformTransactionManager applingTransactionManagerApp() {
        return new DataSourceTransactionManager(applingDateSource());
    }

    @Bean
    public JdbcTemplate applingJdbcTemplate() {
        return new JdbcTemplate(applingDateSource());
    }
}
