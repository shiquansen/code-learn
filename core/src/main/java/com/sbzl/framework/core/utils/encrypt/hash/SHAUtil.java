package com.sbzl.framework.core.utils.encrypt.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.sbzl.framework.core.utils.encrypt.DataFormatUtil.bytesToHex;

public class SHAUtil {

    public static String getSHA1(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(value.getBytes());
        return bytesToHex(md.digest());
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getSHA1("shiquanse"));
    }
}
