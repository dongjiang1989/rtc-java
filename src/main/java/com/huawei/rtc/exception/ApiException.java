package com.huawei.rtc.exception;

public class ApiException extends RTCBaseException {

    private static final long serialVersionUID = 825637445617193691L;

    private final Integer code;
    private final String moreInfo;
    private final Integer status;

    public ApiException(final String message) {
        this(message, null, null, null, null);
    }

    public ApiException(final String message, final Throwable cause) {
        this(message, null, null, null, cause);
    }

    public ApiException(final String message, final Integer code, final String moreInfo, final Integer status,
                        final Throwable cause) {
        super(message, cause);
        this.code = code;
        this.moreInfo = moreInfo;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public Integer getStatusCode() {
        return status;
    }

}
