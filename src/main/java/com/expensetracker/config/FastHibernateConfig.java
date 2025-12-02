// src/main/java/com/example/config/FastHibernateConfig.java
package com.expensetracker.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class FastHibernateConfig {

    @Bean
    public HibernatePropertiesCustomizer fastHibernateCustomizer() {
        return (Map<String, Object> properties) -> {
            // This is the ONLY setting that 100% disables ALL JDBC metadata access at boot
            properties.put(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://dummy"); // fake URL
            properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
            properties.put(AvailableSettings.HBM2DDL_AUTO, "none");
            properties.put("hibernate.connection.provider_disables_autocommit", "true");
        };
    }
}