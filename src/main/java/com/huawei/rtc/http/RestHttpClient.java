package com.huawei.rtc.http;

import com.huawei.rtc.RTC;
import com.huawei.rtc.exception.ApiException;
import com.huawei.rtc.utils.Wsse;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RestHttpClient extends HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(RestHttpClient.class);

    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 30500;

    private String appKey;
    private String appSecret;

    private final org.apache.http.client.HttpClient client;

    /**
     * Create a new HTTP Client.
     */
    public RestHttpClient(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        Collection<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("X-RTC-Client", "java-" + RTC.VERSION));
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT, "rtc-java/" + RTC.VERSION + " (" + RTC.JAVA_VERSION + ")"));
        headers.add(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
        headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "utf-8"));
        headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, Wsse.Authorization()));
        headers.add(new BasicHeader("X-WSSE", Wsse.WsseHeader(appKey, appSecret)));

        org.apache.http.impl.client.HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(10);
        connectionManager.setMaxTotal(10*2);

        clientBuilder
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .setDefaultHeaders(headers);

        client = clientBuilder.build();
    }

    /**
     * Create a new HTTP Client using custom configuration.
     * @param clientBuilder an HttpClientBuilder.
     */
    public RestHttpClient(HttpClientBuilder clientBuilder) {
        Collection<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("X-RTC-Client", "java-" + RTC.VERSION));
        headers.add(new BasicHeader(
                HttpHeaders.USER_AGENT, "rtc-java/" + RTC.VERSION + " (" + RTC.JAVA_VERSION + ") custom"
        ));
        headers.add(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
        headers.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "utf-8"));
        headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, Wsse.Authorization()));
        headers.add(new BasicHeader("X-WSSE", Wsse.WsseHeader(appKey, appSecret)));

        client = clientBuilder
                .setDefaultHeaders(headers)
                .build();
    }

    /**
     * Make a request.
     *
     * @param request request to make
     * @return Response of the HTTP request
     */
    public Response makeRequest(final Request request) {

        HttpMethod method = request.getMethod();
        RequestBuilder builder = RequestBuilder.create(method.toString())
                .setUri(request.constructURL().toString())
                .setVersion(HttpVersion.HTTP_1_1)
                .setCharset(StandardCharsets.UTF_8);

        if (request.requiresAuthentication()) {
            builder.addHeader(HttpHeaders.AUTHORIZATION, request.getAuthString());
        }

        if (method == HttpMethod.POST) {
            builder.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            for (Map.Entry<String, List<String>> entry : request.getPostParams().entrySet()) {
                for (String value : entry.getValue()) {
                    builder.addParameter(entry.getKey(), value);
                }
            }
        }

        HttpResponse response = null;

        try {
            response = client.execute(builder.build());
            HttpEntity entity = response.getEntity();
            return new Response(
                    // Consume the entire HTTP response before returning the stream
                    entity == null ? null : String.valueOf(new BufferedHttpEntity(entity).getContent()),
                    response.getStatusLine().getStatusCode()
            );
        } catch (IOException e) {
            logger.error(e.toString());
            throw new ApiException(e.getMessage(), e);
        } finally {
            // Ensure this response is properly closed
            HttpClientUtils.closeQuietly(response);
        }

    }
}
