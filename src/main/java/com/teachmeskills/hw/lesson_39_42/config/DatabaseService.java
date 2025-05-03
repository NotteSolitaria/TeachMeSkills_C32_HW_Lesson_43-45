package com.teachmeskills.hw.lesson_39_42.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

@Component
@PropertySource("classpath:application.properties")
public class DatabaseService {
    public Environment environment;

    @Autowired
    public DatabaseService(Environment environment) {
        this.environment = environment;
    }

    public DatabaseService(){

    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                    environment.getProperty("spring.datasource.username"),
                    environment.getProperty("spring.datasource.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
