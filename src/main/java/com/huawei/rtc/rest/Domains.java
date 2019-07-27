package com.huawei.rtc.rest;

public enum Domains {
    PNS("rtcapi.cn-north-1.myhuaweicloud.com:12543"),
    SMS("rtcapi.cn-north-1.myhuaweicloud.com:12543");

    private final String value;

    private Domains(final String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}