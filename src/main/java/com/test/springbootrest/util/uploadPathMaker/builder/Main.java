package com.test.springbootrest.util.uploadPathMaker.builder;

public enum Main implements BaseEnumeration {
    USER("users");
    String path;
    Main(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}