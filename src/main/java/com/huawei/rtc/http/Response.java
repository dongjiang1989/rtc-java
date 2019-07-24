package com.huawei.rtc.http;

import com.huawei.rtc.exception.ApiException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Response {
    private final InputStream stream;
    private final String content;
    private final int statusCode;

    public Response(final String content, final int statusCode) {
        this.stream = null;
        this.content = content;
        this.statusCode = statusCode;
    }

    public Response(final InputStream stream, final int statusCode) {
        this.stream = stream;
        this.content = null;
        this.statusCode = statusCode;
    }

    public String getContent() {
        if (content != null) {
            return content;
        }

        if (stream != null) {
            Scanner scanner = new Scanner(stream, "UTF-8").useDelimiter("\\A");

            if (!scanner.hasNext()) {
                return "";
            }

            String data = scanner.next();
            scanner.close();

            return data;
        }

        return "";
    }

    public InputStream getStream() {
        if (stream != null) {
            return stream;
        }
        try {
            return new ByteArrayInputStream(content.getBytes("utf-8"));
        } catch (final UnsupportedEncodingException e) {
            throw new ApiException("UTF-8 encoding not supported", e);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }
}
