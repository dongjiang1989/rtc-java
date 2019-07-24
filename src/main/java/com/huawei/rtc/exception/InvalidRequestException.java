package com.huawei.rtc.exception;

public class InvalidRequestException extends RTCBaseException  {
    private final String param;

    public InvalidRequestException(final String message) {
        super(message, null);
        this.param = null;
    }

    public InvalidRequestException(final String message, final String param) {
        super(message, null);
        this.param = param;
    }

    public InvalidRequestException(final String message, final String param, final Throwable cause) {
        super(message, cause);
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
