package com.huawei.rtc.utils;


import com.huawei.rtc.exception.EmptyIpException;
import com.huawei.rtc.exception.NotFourOctetsException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Random;

public class Utils {

    public static String normalizeBaggageKey(String key) {
        return key.replaceAll("_", "-").toLowerCase(Locale.ROOT);
    }

    public static int ipToInt(String ip) throws EmptyIpException, NotFourOctetsException {
        if (ip.equals("")) {
            throw new EmptyIpException();
        }

        if (ip.equals("localhost")) {
            return (127 << 24) | 1;
        }

        InetAddress octets;
        try {
            octets = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            throw new NotFourOctetsException();
        }

        int intIp = 0;
        for (byte octet : octets.getAddress()) {
            intIp = (intIp << 8) | (octet & 0xFF);
        }
        return intIp;
    }

    public static long uniqueId() {
        long val = 0;
        while (val == 0) {
            Random rand = new Random();
            val = rand.nextLong();
        }
        return val;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private Utils() {}
}