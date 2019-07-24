package com.huawei.rtc.exception;

public class RTCBaseException  extends RuntimeException {

    private static final long serialVersionUID = 5768369629616181639L;

    public RTCBaseException(final String message) {
        this(message, null);
    }

    public RTCBaseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}