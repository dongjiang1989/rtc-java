package com.huawei.rtc;

import com.huawei.rtc.exception.AuthenticationException;

public class RTC {
    public static final String VERSION = "0.0.1";
    public static final String JAVA_VERSION = System.getProperty("java.version");

    private static String accessKeyID;
    private static String secretAccessKey;

    private RTC() {}

    /**
     * Initialize the RTC environment.
     *
     * @param key account to use
     * @param secret auth token for the account
     */
    public static void init(final String key, final String secret) {
        RTC.setAccessKeyId(key);
        RTC.setSecretAccessKey(secret);
    }

    /**
     * Set the access key id.
     *
     * @param key account sid to use
     * @throws AuthenticationException if accessKey is null
     */
    public static void setAccessKeyId(final String key) {
        if (key == null) {
            throw new AuthenticationException("AccessKeyID can not be null");
        }

        if (!key.equals(RTC.accessKeyID)) {
            RTC.invalidate();
        }

        RTC.accessKeyID = key;
    }

    /**
     * Set the secret access key
     *
     * @param secret secret access key to use
     * @throws AuthenticationException if password is null
     */
    public static void setSecretAccessKey(final String secret) {
        if (secret == null) {
            throw new AuthenticationException("SecretAccessKey can not be null");
        }

        if (!secret.equals(RTC.secretAccessKey)) {
            RTC.invalidate();
        }

        RTC.secretAccessKey = secret;
    }

    /**
     * TearDown resource
     */
    private static void invalidate() {
        //TODO
    }
}
