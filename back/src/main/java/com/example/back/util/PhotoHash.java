package com.example.back.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class PhotoHash {
    public static String getPhotoHash(InputStream fis) {
        try {
            byte buffer[] = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int length = -1;
            while((length = fis.read(buffer, 0, 1024)) != -1) {
                md5.update(buffer, 0, length);
            }
            byte[] md5bytes = md5.digest();
            StringBuffer hexValue = new StringBuffer();
            for(int i = 0; i < md5bytes.length; i ++) {
                int val = ((int) md5bytes[i] & 0xff);
                if(val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.getStackTrace();
            return "";
        }
    }
}
