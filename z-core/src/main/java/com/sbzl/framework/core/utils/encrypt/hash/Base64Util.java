package com.sbzl.framework.core.utils.encrypt.hash;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Base64Util {


    public static String encoder(String code){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(code.getBytes());
    }

    public static String decoder(String code) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(code);
        return new String(bytes, StandardCharsets.UTF_8);
    }



    public static void main( String[] args ) throws IOException {
        String name = "时全森";
        System.out.println(encoder(name));
        System.out.println(decoder(encoder(name)));

    }
}
