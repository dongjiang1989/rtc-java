package com.huawei.rtc.rest.sms.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.huawei.rtc.exception.ApiException;
import com.huawei.rtc.exception.RTCBaseException;
import com.huawei.rtc.http.Response;

import java.io.Serializable;
import java.util.List;
import java.io.InputStream;
import java.io.IOException;

public class ResponseImpl extends Response {
    @JsonProperty("result")
    List<SmsID> result;

    public ResponseImpl(String content, int statusCode) {
        super(content, statusCode);
    }

    public static ResponseImpl fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, ResponseImpl.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new RTCBaseException(e.getMessage(), e);
        }
    }

    public  static  class SmsID implements Serializable {
        public String smsMsgId;
        public String from;
        public String origin;
        public String status;
        public String CreateTime;

        public SmsID() {
            //TODO
        }

        public  String toString() {
            return String.format("smsMsdId %s, from %s, origin %s, status %s, CreateTime %s", smsMsgId, from, origin, status, CreateTime);
        }
    }
}
