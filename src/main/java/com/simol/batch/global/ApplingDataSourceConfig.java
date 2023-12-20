package com.simol.batch.global;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.simol.batch.statics",
        entityManagerFactoryRef = "applingEntityManager",
        transactionManagerRef = "applingTransactionManager"
)
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
    @Qualifier("applingDataSource")
    public DataSource applingDateSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Qualifier("applingEntityManager")
    public LocalContainerEntityManagerFactoryBean applingEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(applingDateSource());
        em.setPackagesToScan("com.simol.batch.statics");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPersistenceProvider(new HibernatePersistenceProvider());

        Properties properties = new Properties();
        // 물리적인 네이밍 전략 설정
        properties.setProperty("hibernate.physical_naming_strategy", "com.simol.batch.global.CustomPhysicalNamingStrategy");

        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    @Qualifier("applingTransactionManager")
    public PlatformTransactionManager applingTransactionManager() {
        return new JpaTransactionManager(Objects.requireNonNull(applingEntityManager().getObject()));
    }
}
