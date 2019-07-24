package com.huawei.rtc.metric;

public interface Histogram {
    void update(int value);
    void update(long value);
}
