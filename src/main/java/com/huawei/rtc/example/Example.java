package com.huawei.rtc.example;

import com.huawei.rtc.RTC;
import com.huawei.rtc.rest.sms.v1.ResponseImpl;
import com.huawei.rtc.rest.sms.v1.Sms;

public class Example {
    public static final String APPKEY = "7kd0790497U0gACCyVJ01t9v3b3I";
    public static final String APPSECRET = "L080n0FjqEk4KpL9EG6Kou09R3X5";

    public static void main(String[] args)
    {
        RTC.init(APPKEY, APPSECRET);

        ResponseImpl res = new Sms().setSender("10690400999302429").setReceiver("+8613811400863").setTemplateId("cac5dfcb8f3e4f1f805981272ca342fe").setSignature("\\u534e\\u4e3a\\u4e91\\u77ed\\u4fe1\\u6d4b\\u8bd5")
                .Send(RTC.getClientManager());

        System.out.println(res.toString());
    }
}