package com.huawei.rtc.metric;

import java.util.Map;

public class Metric {
    public String name;

    public String help;

    public Map<String, String> labels;

    public double value;

    public Long timestamp = null;
}
