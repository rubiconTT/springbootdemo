package com.tt.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by TT on 2019/3/31.
 */
@Configuration
@MapperScan(basePackages = "com.tt.dao.*.**")
public class MybatisConfig {

    @Autowired
    @Qualifier("dynamicDataSource")
    private DataSource dynamicDataSource;

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);

        SqlSessionFactory ssf=null;

        try {
            ssf=bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ssf;
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() {
        return new SqlSessionTemplate(sqlSessionFactory());
    }



}
