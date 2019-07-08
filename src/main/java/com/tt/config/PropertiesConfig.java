package com.tt.config;

import com.tt.util.DateUtils;
import com.tt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by TT on 2019/5/14.
 */
@Component
public class PropertiesConfig {

    @Autowired
    private Environment env;

    // ------------------------------- mysql local dataSource config---------------------- //

    public String getLocalUrl() {
        return env.getProperty("datasource.local.url");
    }

    public String getLocalUserName() {
        return env.getProperty("datasource.local.username");
    }

    public String getLocalPasswd() {
        return env.getProperty("datasource.local.password");
    }

    public String getLocalDriverName() {
        return env.getProperty("datasource.local.driverClassName");
    }

    public String getLocalValidationQuery() {
        return env.getProperty("datasource.local.validationQuery");
    }

    // ------------------------------ mysql remote dataSource config------------------------- //

    public String getRemoteUrl() {
        return env.getProperty("datasource.remote.url");
    }

    public String getRemoteUserName() {
        return env.getProperty("datasource.remote.username");
    }

    public String getRemotePasswd() {
        return env.getProperty("datasource.remote.password");
    }

    public String getRemoteDriverName() {
        return env.getProperty("datasource.remote.driverClassName");
    }

    public String getRemoteValidationQuery() {
        return env.getProperty("datasource.remote.validationQuery");
    }











}
