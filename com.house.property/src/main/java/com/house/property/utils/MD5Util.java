package com.house.property.utils;

import java.security.MessageDigest;

public class MD5Util {

    public static String getMD5Str(String str) {
        byte[] digest = null;
        String md5Str = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes("utf-8"));
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < digest.length; i++){
                int val = ((int) digest[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            md5Str = hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

}