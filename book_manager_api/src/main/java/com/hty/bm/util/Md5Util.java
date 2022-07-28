package com.hty.bm.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    private Md5Util() {
    }
    /**
     * 使用md5信息摘要算法加密字符串
     *
     * @param str
     * @return
     */
    public static String genMd5Str(String str) {
        String s = null;
        try {
            //获取到信息摘要算法(md5)的对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(str.getBytes("UTF-8"));
            BigInteger bigInteger = new BigInteger(1, bytes);
            s = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
