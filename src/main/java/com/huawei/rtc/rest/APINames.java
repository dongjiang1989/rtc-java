package com.huawei.rtc.rest;

public enum APINames {
    PNS("pns"),
    SMS("sms");

    private final String value;

    private APINames(final String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}