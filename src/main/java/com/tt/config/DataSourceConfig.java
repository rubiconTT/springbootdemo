package com.tt.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TT on 2019/3/31.
 */
@Log4j2
@MapperScan("com.tt.dao.*..*")
@Configuration
public class DataSourceConfig {


    @Autowired
    private PropertiesConfig pc;

    @Autowired
    private DruidDSConfig dc;

    // -----------------------------------------mysql local dataSource config----------------------
    @Bean(name="localDataSource")
    public DataSource localDataSource(){
        log.info("----------------LOCAL CONFIG: " + pc.getLocalUrl());

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(pc.getLocalUrl());
        datasource.setUsername(pc.getLocalUserName());
        datasource.setPassword(pc.getLocalPasswd());
        datasource.setDriverClassName(pc.getLocalDriverName());
        datasource.setValidationQuery(pc.getLocalValidationQuery());
        // 设置druid数据源的属性
        setDruidOptions(datasource);
        return datasource;
    }

    // ------------------------------mysql remote dataSource config-------------------------

    @Bean(name="remoteDataSource")
    public DataSource remoteDataSource(){
        log.info("----------------REMOTE CONFIG: " + pc.getRemoteUrl());

        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(pc.getRemoteUrl());
        datasource.setUsername(pc.getRemoteUserName());
        datasource.setPassword(pc.getRemotePasswd());
        datasource.setDriverClassName(pc.getRemoteDriverName());
        datasource.setValidationQuery(pc.getRemoteValidationQuery());
        setDruidOptions(datasource);

        return datasource;
    }
    // -----------------------------------------druid config-------------------------------------

    private void setDruidOptions(DruidDataSource datasource){
        datasource.setInitialSize(dc.getDruidInitialSize());
        datasource.setMinIdle(dc.getDruidMinIdle());
        datasource.setMaxActive(dc.getDruidMaxActive());
        datasource.setMaxWait(dc.getDruidMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(dc.getDruidTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(dc.getDruidMinEvictableIdleTimeMillis());
        datasource.setTestWhileIdle(dc.getDruidTestWhileIdle());
        datasource.setTestOnBorrow(dc.getDruidTestOnBorrow());
        datasource.setTestOnReturn(dc.getDruidTestOnReturn());
        datasource.setPoolPreparedStatements(dc.getDruidPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(dc.getDruidMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(dc.getDruidFilters());
        } catch (SQLException e) {
            log.error("druid configuration initialization filter Exception", e);
        }
        datasource.setConnectionProperties(dc.getDruidConnectionProperties());
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    @ConfigurationProperties
    public DataSource primaryDataSource(){
        // 主数据源，优先使用
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        DataSource local = localDataSource();
//
        DataSource remote = remoteDataSource();

        try {
            log.info("------ local connection connected: "+local.getConnection().isValid(120));
            log.info("------ remote connection connected: "+(!remote.getConnection().isClosed()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(remote);

        //配置多个数据源
        Map<Object,Object> map = new HashMap<>();
        map.put(DataSourceType.LOCAL.getTypeName(),local);
        map.put(DataSourceType.REMOTE.getTypeName(),remote);
        dynamicDataSource.setTargetDataSources(map);

        return dynamicDataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        // 事务管理
        return new DataSourceTransactionManager(primaryDataSource());
    }


    @Bean(name="druidServlet")
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", ""); // 白名单
        return reg;
    }

    @Bean(name = "filterRegistrationBean")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("principalCookieName","USER_COOKIE");
        filterRegistrationBean.addInitParameter("principalSessionName","USER_SESSION");
        filterRegistrationBean.addInitParameter("DruidWebStatFilter","/*");
        return filterRegistrationBean;
    }

}
