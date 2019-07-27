package com.huawei.rtc.rest.sms.v1;

import com.huawei.rtc.http.RTCManager;
import com.huawei.rtc.http.Request;
import com.huawei.rtc.http.HttpMethod;
import com.huawei.rtc.http.Response;
import com.huawei.rtc.rest.Domains;
import com.huawei.rtc.exception.ApiException;
import com.huawei.rtc.exception.RestException;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.Serializable;

public class Sms implements Serializable {
    private static final long serialVersionUID = 5926257047947205316L;

    private String url;
    private String sender;
    private String receiver;
    private String templateId;
    private String signature;
    private int StateCode;

    public boolean Send(final RTCManager clientManger) {
        Request request = new Request(
                HttpMethod.POST,
                Domains.SMS.toString(),
                "/sms"
        );

        addPostParams(request);
        Response response = clientManger.request(request);

        if (response == null) {
            throw new ApiException("Service creation failed: Unable to connect to server");
        } else if (response.getStatusCode() != 200) {
            RestException restException = RestException.fromJson(response.getStream(), clientManger.getObjectMapper());
            if (restException == null) {
                throw new ApiException("Server Error, no content");
            }

            throw new ApiException(
                    restException.getMessage(),
                    restException.getCode(),
                    restException.getMoreInfo(),
                    restException.getStatus(),
                    null
            );
        }

        return fromJson(response.getStream(), clientManger.getObjectMapper());
    }

    public static  fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Service.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    public boolean addPostParams(Request request) {
        request.addPostParam("from", sender);
        request.addPostParam("to", receiver);
        request.addPostParam("templateId", templateId);
        return true;
    }
}
