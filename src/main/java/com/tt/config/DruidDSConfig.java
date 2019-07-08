package com.tt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by TT on 2019/5/15.
 */
@Component
public class DruidDSConfig {

    // ------------------------------ druid config ------------------------- //

    @Value("${datasource.druid.type}")
    private String druidType;

    @Value("${datasource.druid.initialSize}")
    private int initialSize;

    @Value("${datasource.druid.minIdle}")
    private int minIdle;

    @Value("${datasource.druid.maxActive}")
    private int maxActive;

    @Value("${datasource.druid.maxWait}")
    private int maxWait;

    @Value("${datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${datasource.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${datasource.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${datasource.druid.filters}")
    private String filters;

    @Value("{datasource.druid.connectionProperties}")
    private String connectionProperties;

    @Value("${datasource.druid.useGlobalDataSourceStat}")
    private boolean useGlobalDataSourceStat;


    public String getDruidType(){
        return druidType;
    }
    public int getDruidInitialSize(){
        return initialSize;
    }
    public int getDruidMinIdle(){
        return  minIdle;
    }
    public int getDruidMaxActive(){
        return  maxActive;
    }
    public int getDruidMaxWait(){
        return maxWait;
    }
    public int getDruidTimeBetweenEvictionRunsMillis(){
        return  timeBetweenEvictionRunsMillis;
    }
    public int getDruidMinEvictableIdleTimeMillis(){
        return  minEvictableIdleTimeMillis;
    }
    public boolean getDruidTestWhileIdle(){
        return  testWhileIdle;
    }
    public boolean getDruidTestOnBorrow(){
        return  testOnBorrow;
    }
    public boolean getDruidTestOnReturn(){
        return testOnReturn;
    }
    public boolean getDruidPoolPreparedStatements(){
        return poolPreparedStatements;
    }
    public int getDruidMaxPoolPreparedStatementPerConnectionSize(){
        return maxPoolPreparedStatementPerConnectionSize;
    }
    public String getDruidFilters(){
        return filters;
    }
    public String getDruidConnectionProperties(){
        return connectionProperties;
    }
    public boolean getDruidUseGlobalDataSourceStat(){
        return  useGlobalDataSourceStat;
    }
}
