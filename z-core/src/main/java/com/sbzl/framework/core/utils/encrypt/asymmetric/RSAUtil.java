package com.sbzl.framework.core.utils.encrypt.asymmetric;


import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class RSAUtil {
    public static final String ENCRYPTION_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 生成密钥
     */
    public static Map<String, Object> initKey() throws Exception {
        /* 初始化密钥生成器 */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyPairGenerator.initialize(1024);
        /* 生成密钥 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("PublicKey", publicKey);
        keyMap.put("PrivateKey", privateKey);
        return keyMap;
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("PublicKey");
        return Base64Util.encryptBASE64(key.getEncoded());
    }

    /**
     * 取得私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("PrivateKey");
        return Base64Util.encryptBASE64(key.getEncoded());
    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] data, String keyString, boolean isPublic) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = RSAUtil.getKeyFactory(keyAndFactoryMap);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] data, String keyString, boolean isPublic) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = RSAUtil.getKeyFactory(keyAndFactoryMap);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 生成钥匙
     */
    public static Map<String, Object> generateKeyAndFactory(String keyString, boolean isPublic) throws Exception {
        byte[] keyBytes = Base64Util.decryptBASE64(keyString);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        Key key = null;
        if (isPublic) {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(x509KeySpec);
        } else {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(pkcs8KeySpec);
        }
        Map<String, Object> keyAndFactoryMap = new HashMap<String, Object>(2);
        keyAndFactoryMap.put("key", key);
        keyAndFactoryMap.put("keyFactory", keyFactory);
        return keyAndFactoryMap;
    }

    /**
     * 从指定对象中获取钥匙
     */
    public static Key getKey(Map<String, Object> map) {
        if (map.get("key") == null) {
            return null;
        }
        return (Key)map.get("key");
    }

    /**
     * 从指定对象中获取钥匙工厂
     */
    public static KeyFactory getKeyFactory(Map<String, Object> map) {
        if (map.get("keyFactory") == null) {
            return null;
        }
        return (KeyFactory)map.get("keyFactory");
    }

    /**
     * 对信息生成数字签名（用私钥）
     */
    public static String sign(byte[] data, String keyString) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, false);
        Key key = RSAUtil.getKey(keyAndFactoryMap);
        PrivateKey privateKey = (PrivateKey)key;
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return Base64Util.encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名（用公钥）
     */
    public static boolean verify(byte[] data, String keyString, String sign)
            throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, true);
        Key key = RSAUtil.getKey(keyAndFactoryMap);
        PublicKey publicKey = (PublicKey)key;
        // 取公钥匙对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(Base64Util.decryptBASE64(sign));
    }


    public static void main(String[] args) throws Exception {

        Map<String, Object> keyMap = RSAUtil.initKey();
        String publicKey = RSAUtil.getPublicKey(keyMap);
        String privateKey = RSAUtil.getPrivateKey(keyMap);
        System.out.println("公钥 -> " + publicKey);
        System.out.println("私钥 -> " + privateKey);


     /*   System.out.println("公钥加密，私钥解密");
        String sourceString = "hi, RSA";
        byte[] encodedData = RSAUtil.encrypt(sourceString.getBytes(), publicKey, true);
        byte[] decodedData = RSAUtil.decrypt(encodedData, privateKey, false);
        String targetString = new String(decodedData);
        System.out.println("加密前: " + sourceString + "，解密后: " + targetString);
        assert sourceString.equals(targetString);*/


        /*System.out.println("私钥签名，公钥验证签名");
        String sourceString = "hello, RSA sign";
        byte[] data = sourceString.getBytes();
        // 产生签名
        String sign = RSAUtil.sign(data, privateKey);
        System.out.println("签名 -> " + sign);
        // 验证签名
        boolean status = RSAUtil.verify(data, publicKey, sign);
        System.out.println("状态 -> " + status);*/


        System.out.println("私钥加密，公钥解密");
        String sourceString = "hello, reRSA";
        byte[] data = sourceString.getBytes();
        byte[] encodedData = RSAUtil.encrypt(data, privateKey, false);
        byte[] decodedData = RSAUtil.decrypt(encodedData, publicKey, true);
        String targetString = new String(decodedData);
        System.out.println("加密前: " + sourceString + "，解密后: " + targetString);
        assert sourceString.equals(targetString);

    }

}