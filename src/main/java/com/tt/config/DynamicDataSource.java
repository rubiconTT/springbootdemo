package com.tt.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by TT on 2019/3/31.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /** 数据源路由，此方法用于产生要选取的数据源逻辑名称 */
    @Override
    protected Object determineCurrentLookupKey() {
        return JdbcContextHolder.getDataSource();
    }
}
