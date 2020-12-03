package com.sbzl.framework.core.utils.encrypt.hash;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.sbzl.framework.core.utils.encrypt.DataFormatUtil.bytesToHex;

public class MD5Util {
    /**
     * 生成MD5工具
     * @param value
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMd5(String value) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = messageDigest.digest(value.getBytes(Charset.forName("UTF-8")));
        return bytesToHex(md5Bytes);
    }

    public static String getMd2(String value) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD2");
        byte[] md2Bytes = messageDigest.digest(value.getBytes(Charset.forName("UTF-8")));
        return bytesToHex(md2Bytes);
    }



    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getMd2("shiquansen"));
    }
}
