package com.huawei.rtc.rest.sms.v1;

import com.huawei.rtc.http.RTCManager;
import com.huawei.rtc.http.Request;
import com.huawei.rtc.http.HttpMethod;
import com.huawei.rtc.http.Response;
import com.huawei.rtc.rest.Domains;
import com.huawei.rtc.exception.ApiException;
import com.huawei.rtc.exception.RestException;


import java.io.Serializable;

public class Sms implements Serializable {
    private static final long serialVersionUID = 5926257047947205316L;

    private String sender;
    private String receiver;
    private String templateId;
    private String signature;

    public Sms setSender(final String sender) {
        this.sender = sender;
        return this;
    }

    public Sms setReceiver(final String receiver) {
        this.receiver = receiver;
        return this;
    }

    public Sms setTemplateId(final String tid) {
        this.templateId = tid;
        return this;
    }

    public Sms setSignature(final String sign) {
        this.signature = sign;
        return this;
    }

    public ResponseImpl Send(final RTCManager clientManger) {
        Request request = new Request(
                HttpMethod.POST,
                Domains.SMS.toString(),
                "sms/batchSendSms/v1"
        );

        addPostParams(request);
        System.out.println(request.getUrl());
        System.out.println(request.getPostParams());
        Response response = clientManger.request(request);

        if (response == null) {
            throw new ApiException("Service creation failed: Unable to connect to server");
        } else if (response.getStatusCode() != 200) {
            System.out.println(response.toString());
            System.out.println(response.getStatusCode());
            System.out.println(response.getStream());
            System.out.println(response.getContent()); //TODO delete
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

        return ResponseImpl.fromJson(response.getStream(), clientManger.getObjectMapper());
    }


    public boolean addPostParams(Request request) {
        request.addPostParam("from", sender);
        request.addPostParam("to", receiver);
        request.addPostParam("templateId", templateId);
        request.addPostParam("signature", signature);
        return true;
    }

}
