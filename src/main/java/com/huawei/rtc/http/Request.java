package com.huawei.rtc.http;

import com.huawei.rtc.exception.InvalidRequestException;
import com.huawei.rtc.exception.ApiException;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class Request {

    public static final String QUERY_STRING_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String QUERY_STRING_DATE_FORMAT = "yyyy-MM-dd";

    private final HttpMethod method;
    private final String url;
    private final Map<String, List<String>> queryParams;
    private final Map<String, List<String>> postParams;

    private String username;
    private String password;

    /**
     * Create a new API request.
     *  @param method HTTP method
     *  @param url url of request
     */
    public Request(final HttpMethod method, final String url) {
        this.method = method;
        this.url = url;
        this.queryParams = new HashMap<>();
        this.postParams = new HashMap<>();
    }

    /**
     * Create a new API request.
     * @param method HTTP Method
     * @param domain domain
     * @param uri uri of request
     */
    public Request(final HttpMethod method, final String domain, final String uri) {
        this.method = method;
        this.url = new StringJoiner("/", "https://", "/").add(domain.trim()).toString() + uri;
        this.queryParams = new HashMap<>();
        this.postParams = new HashMap<>();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public void setAuth(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Create auth string from username and password.
     *
     * @return basic authentication string
     */
    public String getAuthString() {
        String credentials = this.username + ":" + this.password;
        try {

            String encoded = DatatypeConverter.printBase64Binary(credentials.getBytes("ascii"));
            return "Basic " + encoded;

        } catch (final UnsupportedEncodingException e) {

            throw new InvalidRequestException("It must be possible to encode credentials as ascii", credentials, e);

        }
    }

    public boolean requiresAuthentication() {
        return username != null || password != null;
    }

    /**
     * Build the URL for the request.
     *
     * @return URL for the request
     */
    @SuppressWarnings("checkstyle:abbreviationaswordinname")
    public URL constructURL() {
        String params = encodeQueryParams();
        String stringUri = url;

        if (params.length() > 0) {
            stringUri += "?" + params;
        }

        try {
            URI uri = new URI(stringUri);
            return uri.toURL();
        } catch (final URISyntaxException e) {
            throw new ApiException("Bad URI: " + stringUri, e);
        } catch (final MalformedURLException e) {
            throw new ApiException("Bad URL: " + stringUri, e);
        }
    }

    /**
     * Add a query parameter.
     *
     * @param name name of parameter
     * @param value value of parameter
     */
    public void addQueryParam(final String name, final String value) {
        addParam(queryParams, name, value);
    }

    /**
     * Add a form parameter.
     *
     * @param name name of parameter
     * @param value value of parameter
     */
    public void addPostParam(final String name, final String value) {
        addParam(postParams, name, value);
    }

    private void addParam(final Map<String, List<String>> params, final String name, final String value) {
        if (!params.containsKey(name)) {
            params.put(name, new ArrayList<String>());
        }

        params.get(name).add(value);
    }

    /**
     * Encode the form body.
     *
     * @return url encoded form body
     */
    public String encodeFormBody() {
        return encodeParameters(postParams);
    }

    /**
     * Encode the query parameters.
     *
     * @return url encoded query parameters
     */
    public String encodeQueryParams() {
        return encodeParameters(queryParams);
    }

    private static String encodeParameters(final Map<String, List<String>> params) {
        StringJoiner sj = new StringJoiner("&");
        for (final Map.Entry<String, List<String>> entry : params.entrySet()) {
            try {
                String encodedName = URLEncoder.encode(entry.getKey(), "UTF-8");
                for (final String value : entry.getValue()) {
                    if (value == null) {
                        continue;
                    }

                    String encodedValue = URLEncoder.encode(value, "UTF-8");
                    sj.add(encodedName + "=" + encodedValue);
                }
            } catch (final UnsupportedEncodingException e) {
                throw new InvalidRequestException("Couldn't encode params", entry.getKey(), e);
            }
        }
        return sj.toString();

    }

    public Map<String, List<String>> getQueryParams() {
        return queryParams;
    }

    public Map<String, List<String>> getPostParams() {
        return postParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Request other = (Request) o;
        return Objects.equals(this.method, other.method) &&
                Objects.equals(this.url, other.url) &&
                Objects.equals(this.username, other.username) &&
                Objects.equals(this.password, other.password) &&
                Objects.equals(this.queryParams, other.queryParams) &&
                Objects.equals(this.postParams, other.postParams);
    }
}
