package com.huawei.rtc.exception;

public class NotFourOctetsException  extends RTCBaseException {

    private static final long serialVersionUID = 3303277253244490515L;

    public NotFourOctetsException() {
        super("Wrong number of octets");
    }
}
