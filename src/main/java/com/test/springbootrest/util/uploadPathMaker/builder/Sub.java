package com.test.springbootrest.util.uploadPathMaker.builder;

public enum Sub implements BaseEnumeration {
    THUMBNAIL("thumbs");

    String path;

    Sub(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}