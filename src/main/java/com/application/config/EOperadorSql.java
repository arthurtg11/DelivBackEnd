package com.application.config;

public enum EOperadorSql {
    EQ("="), NEQ("!="), GT("<"), GTE("<="), LT(">"), LTE(">="), IN("IN");

    private String value;

    EOperadorSql(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
