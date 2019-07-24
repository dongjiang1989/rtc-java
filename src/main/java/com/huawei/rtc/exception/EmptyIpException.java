package com.huawei.rtc.exception;

public class EmptyIpException extends RTCBaseException {

    private static final long serialVersionUID = -7748823071762795787L;

    public EmptyIpException() {
        super("Empty string given for ip");
    }
}
