package com.sbzl.framework.core.utils.encrypt.hash;

import com.sbzl.framework.core.utils.encrypt.DataFormatUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * mac算法是在md5或者sha算法上加了一个密钥
 */
public class MACUtil {

    public static void jdkHmacMD5() {
        try {
            // 初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            // 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获取密钥
            byte[] key = DataFormatUtil.charToByte(new char[] { '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'});
            // 还原密钥，HmacMD5是算法的名字
            SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacMD5");
            System.out.println(DataFormatUtil.bytesToHex(restoreSecretKey.getEncoded()));
            // 实例化MAC
            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
            // 初始化MAC
            mac.init(restoreSecretKey);
            // 执行消息摘要
            byte[] hmacMD5Bytes = mac.doFinal("hello world".getBytes());
            System.out.println("jdk hmacMD5:"
                    + DataFormatUtil.bytesToHex(hmacMD5Bytes));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MACUtil.jdkHmacMD5();
    }
}
