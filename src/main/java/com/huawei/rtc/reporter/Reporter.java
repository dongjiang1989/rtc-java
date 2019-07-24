package com.huawei.rtc.reporter;

public interface Reporter {
    void report(String message);
    void close();
}
