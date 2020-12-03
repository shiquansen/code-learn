package com.sbzl.framework.core.utils.encrypt.symmetrical;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class AESUtil {
    /*
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String encrypt(String pwd ,String content){

        try {
            //创建AES的key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //利用密码作为随机数初始化出128位的key生产者
            //加密没关闭，SecureRandom是生成安全随机数序列，pwd.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有pwd就行
            kgen.init(128,new SecureRandom(pwd.getBytes()));
            //
            SecretKey secretKey = kgen.generateKey();//根据密码生成一个密钥
            byte [] enCodeFormat = secretKey.getEncoded();//返回将基本格式的密钥，如果此密钥不支持编码，则返回null
            //转化为AES专用密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat,"AES");
            //创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            //初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE,key);
            //获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteContent = content.getBytes("utf-8");

            //加密
            byte[] result =cipher.doFinal(byteContent);
            //将加密后的数据转为字符串
            String s = new String (new BASE64Encoder().encode(result));

            return s;
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String decrypt(String pwd,String content) {
        String re = "";
        try {
            //1.构成密钥生成器，指定AES算法，不区分大小写
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //2.根据pwd初始化密钥生成器
            //生成一个128位的随机源，根据传入的字节数组
            kgen.init(128, new SecureRandom(pwd.getBytes()));
            //3.产生原始对称密钥
            SecretKey ori_key = kgen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = ori_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            //7. 初始化密码器，第一个参数为加密模式，第二个为使用的key
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            //解密
            byte[] result = cipher.doFinal(byte_content);
            re = new String(result, "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return re;
    }


    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        //加密
        System.out.println("使用AES 对称加密，请输入加密的规则：");
        String key = scanner.next();
        System.out.println("请输入要加密的内容：");
        String content = scanner.next();
        String re = encrypt(key,content);
        System.out.println("根据输入的key加密后的密文："+ re);

        //解密
        System.out.println("使用AES对称解密，请输入加密的密钥：与加密时的密钥一致");
        key = scanner.next();
        System.out.println("请输入要解密的密文：");
        String le=  decrypt(key,re);
        System.out.println("根据输入的密钥key解密后的明文是："+le);
    }
}
