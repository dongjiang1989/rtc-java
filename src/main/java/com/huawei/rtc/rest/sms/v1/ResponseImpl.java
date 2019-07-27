package com.huawei.rtc.rest.sms.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ResponseImpl extends com.huawei.rtc.http.Response {
    @JsonProperty("result")
    List<SmsID> result;

    public ResponseImpl () {
        super();
    }

    public  static  class SmsID implements Serializable {
        public String smsMsgId;
        public String from;
        public String origin;
        public String status;
        public String CreateTime;

        public SmsID() {

        }

        public  String toString() {
            return String.format("smsMsdId %s", smsMsgId);
        }
    }
}
