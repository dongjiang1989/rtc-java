package com.huawei.rtc.rest;

public enum Domains {
    API("api"),
    SMS("sms");

    private final String value;

    private Domains(final String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}