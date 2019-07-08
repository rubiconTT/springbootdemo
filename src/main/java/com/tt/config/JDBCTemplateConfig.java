package com.tt.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by TT on 2019/3/29.
 */
@Configuration
@EnableTransactionManagement
public class JDBCTemplateConfig {

    @Autowired
    private PropertiesConfig pc;

    @Autowired
    private HikariDSConfig hc;

    @Bean(name="local")
    @Qualifier("local")
    public DataSource localDataSource(){

        HikariConfig hikariConfig=new HikariConfig();
        hikariConfig.setAutoCommit(hc.isHikariAutoCommit());
        hikariConfig.setConnectionInitSql("");
        hikariConfig.setConnectionTestQuery(hc.getHikariTestQuery());
        hikariConfig.setDriverClassName(pc.getLocalDriverName());
        hikariConfig.setIdleTimeout(hc.getHikariIdleTimeout());
        hikariConfig.setJdbcUrl(pc.getLocalUrl());
        hikariConfig.setMaximumPoolSize(hc.getHikariMaxPoolSize());
        hikariConfig.setMaxLifetime(hc.getHikariMaxLifeTime());
        hikariConfig.setPassword(pc.getLocalPasswd());
        hikariConfig.setUsername(pc.getLocalUserName());


        HikariDataSource hds=new HikariDataSource(hikariConfig);


        return hds;
    }

    @Bean(name="localJdbcTemplate")
    public JdbcTemplate localJdbcTemplate(@Qualifier("localDataSource") DataSource localDS){

        return new JdbcTemplate(localDS);
    }

    @Bean(name="remoteJdbcTemplate")
    public JdbcTemplate remoteJdbcTemplate(@Qualifier("remoteDataSource") DataSource remoteDS){
        return new JdbcTemplate(remoteDS);
    }

}
