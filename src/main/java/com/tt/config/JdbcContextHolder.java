package com.tt.config;

/**
 * Created by TT on 2019/3/31.
 */
public class JdbcContextHolder {

    /** 本地线程共享对象（保证在同一线程下切换后不要被其他线程修改） */
    private static final ThreadLocal<String> threadLocal=new ThreadLocal<>();

    public static void putDataSource(String dsName){
        threadLocal.set(dsName);
    }

    public static String getDataSource(){
        return threadLocal.get();
    }

    public static void removeDataSource(){
        threadLocal.remove();
    }
}
