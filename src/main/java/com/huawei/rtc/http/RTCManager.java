package com.huawei.rtc.http;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RTCManager {

    public static final int HTTP_STATUS_CODE_CREATED = 201;
    public static final int HTTP_STATUS_CODE_NO_CONTENT = 204;
    public static final int HTTP_STATUS_CODE_OK = 200;

    private final ObjectMapper objectMapper;
    private final String appKey;
    private final String appSecret;
    private final HttpClient httpClient;

    private RTCManager(Builder b) {
        this.appKey = b.appKey;
        this.appSecret = b.appSecret;
        this.httpClient = b.httpClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Make a request
     *
     * @param request request to make
     * @return Response object
     */
    public Response request(final Request request) {
        return httpClient.reliableRequest(request);
    }


    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public static class Builder {
        private String appKey;
        private String appSecret;
        private HttpClient httpClient;

        /**
         * Create a new Client Manager.
         *
         * @param appKey applictaion key
         * @param appSecret application secret
         */
        public Builder(String appKey, String appSecret) {
            this.appKey = appKey;
            this.appSecret = appSecret;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public RTCManager build() {
            if (this.httpClient == null) {
                this.httpClient = new RestHttpClient(this.appKey, this.appSecret);
            }
            return new RTCManager(this);
        }
    }

}