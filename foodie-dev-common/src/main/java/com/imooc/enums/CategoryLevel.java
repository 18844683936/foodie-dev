package com.imooc.enums;

public enum CategoryLevel {
    root(1,"root");

    public final Integer type;
    public final String value;

    CategoryLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
