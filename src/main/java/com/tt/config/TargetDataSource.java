package com.tt.config;


import java.lang.annotation.*;

/**
 * Created by TT on 2019/3/31.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TargetDataSource {

    DataSourceType value() default DataSourceType.REMOTE;
}
