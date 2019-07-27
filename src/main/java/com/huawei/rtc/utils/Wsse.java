package com.huawei.rtc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class Wsse {

    private static final Logger logger = LoggerFactory.getLogger(Wsse.class);

    private static final String STRING_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String STRING_DATE_FORMAT = "yyyy-MM-dd";
    private static final String WSSE_HEADER_Format = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    private static final String AUTHORIZATION_HEADER_Format = "WSSE realm=\"%s\", profile=\"%s\", type=\"%s\"";

    public static String WsseHeader(String key, String secret) {
        SimpleDateFormat sdf = new SimpleDateFormat(STRING_DATE_TIME_FORMAT);
        String time = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-","");

        MessageDigest md;
        byte[] passwordDigest = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((uuid + time+secret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest);
        return String.format(WSSE_HEADER_Format, key, passwordDigestBase64Str, uuid, time);
    }


    public static String Authorization() {
        return String.format(AUTHORIZATION_HEADER_Format, "SDP", "UsernameToken", "Appkey");
    }
}