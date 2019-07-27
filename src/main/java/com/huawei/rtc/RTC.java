package com.huawei.rtc;

import com.huawei.rtc.exception.AuthenticationException;
import com.huawei.rtc.http.RTCManager;
import com.huawei.rtc.http.HttpMethod;
import com.huawei.rtc.http.Request;
import com.huawei.rtc.http.Response;
import com.huawei.rtc.http.RestHttpClient;
import com.huawei.rtc.exception.ApiException;
import com.huawei.rtc.exception.CertificateValidationException;

public class RTC {
    public static final String VERSION = "0.0.1";
    public static final String JAVA_VERSION = System.getProperty("java.version");

    private static String accessKeyID;
    private static String secretAccessKey;

    private static RTCManager clientManager;


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

    public static RTCManager getClientManager() {
        if (RTC.clientManager == null) {
            if (RTC.accessKeyID == null || RTC.secretAccessKey == null) {
                throw new AuthenticationException(
                        "RTC ClientManager was used before accessKeyID and secretAccessKey were set, please call RTC.init()"
                );
            }

            RTCManager.Builder builder = new RTCManager.Builder(RTC.accessKeyID, RTC.secretAccessKey);
            RTC.clientManager = builder.build();
        }

        return RTC.clientManager;
    }

    public static void setClientManager(final RTCManager clientManager) {
        RTC.clientManager = clientManager;
    }

    public static void validateSslCertificate() throws CertificateValidationException {
        final RestHttpClient client = new RestHttpClient(RTC.accessKeyID, RTC.secretAccessKey);
        final Request request = new Request(HttpMethod.GET, "https://www.baidu.com:8443");

        try {
            final Response response = client.makeRequest(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                throw new CertificateValidationException("Unexpected response from certificate endpoint", request, response);
            }
        } catch (final ApiException e) {
            throw new CertificateValidationException("Could not get response from certificate endpoint", request);
        }
    }

    /**
     * TearDown resource
     */
    private static void invalidate() {
        RTC.clientManager = null;
    }
}
