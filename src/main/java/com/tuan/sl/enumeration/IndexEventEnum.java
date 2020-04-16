package com.tuan.sl.enumeration;

public enum IndexEventEnum {
    ADD("insert"),
    UPDATE("update"),
    DELETE("delete");

    private String value;
    private IndexEventEnum(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
