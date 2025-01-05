package com.fastcampus.boardserver.util;

import lombok.extern.log4j.Log4j2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
public class SHA256Util {
    public static final String ENCRYPTION_KEY = "SHA-256";
    public static String encryptSHA256(String str) {
        String SHA = null;

        MessageDigest sh;

        try {
            sh = MessageDigest.getInstance(ENCRYPTION_KEY);
            sh.update(str.getBytes());
            byte[] digest = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(byte byteDatum : digest) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("encryptSHA256 ERROR : {}", e.getMessage());
            SHA = null;
        }
        return SHA;
    }
}
