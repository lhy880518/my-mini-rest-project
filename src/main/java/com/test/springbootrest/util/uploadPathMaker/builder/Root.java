package com.test.springbootrest.util.uploadPathMaker.builder;

public enum Root implements BaseEnumeration{
    ISW_PRIVATE("isw-private");
    String path;
    Root(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
