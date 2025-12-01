package com.expensetracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class JpaConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory(EntityManagerFactory emf) {
        // This delays ALL @Entity scanning and validation until first query
        emf.getMetamodel().getEntities(); // no-op, just forces lazy init later
        return emf;
    }
}