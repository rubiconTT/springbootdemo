package com.tt.config;

/**
 * Created by TT on 2019/3/31.
 */
public enum DataSourceType {
    LOCAL("local"),
    REMOTE("remote");

    private String typeName;

    DataSourceType(String typeName){
        this.typeName=typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
