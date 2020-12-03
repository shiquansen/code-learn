#### 常用加密
##### 对称加密 #####
- DES 已破解，不再安全，基本没有企业在用了
是对称加密算法的基石，具有学习价值 密钥长度56（JDK）、56/64（BC）
- DES ede，（三重DES）早于AES出现来替代DES
计算密钥时间太长、加密效率不高，所以也基本上不用
密钥长度112/168（JDK）、128/192（BC）
- AES 最常用的对称加密算法 密钥建立时间短、灵敏性好、内存需求低（不管怎样，反正就是好）
实际使用中，使用工作模式为CTR（最好用BC去实现），此工作模式需要引入IV参数（16位的字节数组）
密钥长度128/192/256，其中192与256需要配置无政策限制权限文件（JDK6）
填充模式最常用的两种PKCS5Padding和PKCS7Padding，其中后者只有BC独有。
- IDEA 常用的电子邮件加密算法 工作模式只有ECB 密钥长度128位
5、PBE 综合了消息摘要算法和对称加密算法，最常见的是PBEWithMD5AndDES
工作模式只有CBC（已丧失安全性，不推荐使用），所以PBE也不推荐使用了
##### 非对称加密 #####

##### 摘要 #####
- MD(Message Digest)：消息摘要
    - MD5
    - MD2
    - MD4
- SHA(Secure Hash Algorithm)：安全散列
    - SHA-1           160   SHA1不安全
    - SHA-256         256   SHA2目前安全
    - SHA-384         384
    - SHA-512         512
    - SHA-224         224
- MAC(Message Authentication Code)：消息认证码
    - HmacMD5	128
    - HmacSHA1	160
    - HmacSHA256	256
    - HmacSHA384	384
    - HmacSHA512	512
    - HmacMD2	128
    - HmacMD4	128
    - HmacSHA224	224
    
##### 数字签名
- http://www.ruanyifeng.com/blog/2011/08/what_is_a_digital_signature.html