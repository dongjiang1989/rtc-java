package com.huawei.rtc.metric;


public interface Counter {

    void inc();

    void inc(long n);

    void dec();

    void dec(long n);

}
