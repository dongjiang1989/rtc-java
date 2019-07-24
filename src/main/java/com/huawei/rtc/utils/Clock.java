package com.huawei.rtc.utils;

public class Clock {

    public long currentTimeMicros() {
        return System.currentTimeMillis() * 1000;
    }

    public long currentNanoTicks() {
        return System.nanoTime();
    }

    public boolean isMicrosAccurate() {
        return false;

    }
}
