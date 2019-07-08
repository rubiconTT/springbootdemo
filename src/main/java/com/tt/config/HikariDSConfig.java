package com.tt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by TT on 2019/5/15.
 */
@Component
public class HikariDSConfig {

    @Value("${spring.datasource.type}")
    private String hikariType;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int hikariMinIdle;
    @Value("${spring.datasource.hikari.idle-timeout}")
    private int hikariIdleTimeout;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int hikariMaxPoolSize;
    @Value("${spring.datasource.hikari.auto-commit}")
    private boolean hikariAutoCommit;
    @Value("${spring.datasource.hikari.pool-name}")
    private String hikariPoolName;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private int hikariMaxLifeTime;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private int hikariConTimeout;
    @Value("${spring.datasource.hikari.connection-test-query}")
    private String hikariTestQuery;


    public String getHikariType() {
        return hikariType;
    }

    public int getHikariMinIdle() {
        return hikariMinIdle;
    }

    public int getHikariIdleTimeout() {
        return hikariIdleTimeout;
    }

    public int getHikariMaxPoolSize() {
        return hikariMaxPoolSize;
    }

    public boolean isHikariAutoCommit() {
        return hikariAutoCommit;
    }

    public String getHikariPoolName() {
        return hikariPoolName;
    }

    public int getHikariMaxLifeTime() {
        return hikariMaxLifeTime;
    }

    public int getHikariConTimeout() {
        return hikariConTimeout;
    }

    public String getHikariTestQuery() {
        return hikariTestQuery;
    }
}
