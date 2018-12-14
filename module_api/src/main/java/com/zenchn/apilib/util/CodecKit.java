package com.zenchn.apilib.util;

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 作    者：wangr on 2017/5/26 13:54
 * 描    述：加密方法的封装
 * 修订记录：
 */
public class CodecKit {

    private static final String TAG = "CodecKit";

    /**
     * 加密
     *
     * @param data
     * @param algorithm
     * @return
     */
    private static String encrypt(String data, String algorithm) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = data.getBytes();
        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * 转化成十六进制
     *
     * @param bts
     * @return
     */
    private static String bytes2Hex(byte[] bts) {
        StringBuffer sb = new StringBuffer();
        int length = bts.length;
        for (int i = 0; i < length; i++) {
            int val = ((int) bts[i]) & 0xff;
            if (val < 16)
                sb.append("0");
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    /**
     * 作    者：wangr on 2017/5/31 15:39
     * 描    述：MD5加密的封装
     * 修订记录：
     */
    public static class MD5 {

        /**
         * MD5加密
         *
         * @param data
         * @return
         */
        public static String encrypt(String data) {
            return CodecKit.encrypt(data, "MD5");
        }

        public static String encrypt(final InputStream is, final int bufLen) {
            if (is == null || bufLen <= 0) {
                return null;
            }
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                StringBuilder md5Str = new StringBuilder(32);

                byte[] buf = new byte[bufLen];
                int readCount = 0;
                while ((readCount = is.read(buf)) != -1) {
                    md.update(buf, 0, readCount);
                }

                byte[] hashValue = md.digest();

                for (int i = 0; i < hashValue.length; i++) {
                    md5Str.append(Integer.toString((hashValue[i] & 0xff) + 0x100, 16).substring(1));
                }
                return md5Str.toString();
            } catch (Exception e) {
                return null;
            } finally {
                JavaKit.close(is);
            }
        }

        /**
         * 计算文件md5
         *
         * @param filePath 文件路径
         * @return
         */
        public static String encryptFile(final String filePath) {
            if (filePath == null) {
                return null;
            }

            File f = new File(filePath);
            if (f.exists()) {
                return encrypt(f, 1024 * 100);
            }
            return null;
        }

        /**
         * 计算文件md5
         *
         * @param file
         * @return
         */
        public static String encrypt(final File file) {
            return encrypt(file, 1024 * 100);
        }


        private static String encrypt(final File file, final int bufLen) {
            if (file == null || bufLen <= 0 || !file.exists()) {
                return null;
            }

            FileInputStream fin = null;
            try {
                fin = new FileInputStream(file);
                String md5 = encrypt(fin, (int) (bufLen <= file.length() ? bufLen : file.length()));

                return md5;

            } catch (Exception e) {
                return null;

            } finally {
                JavaKit.close(fin);
            }
        }

    }

    /**
     * 作    者：wangr on 2017/5/31 16:36
     * 描    述：SHA1加密
     * 修订记录：
     */
    public static class SHA1 {

        public static String encrypt(String data) {
            return CodecKit.encrypt(data, "SHA-1");
        }

    }

    /**
     * 作    者：wangr on 2017/5/31 16:39
     * 描    述：SHA256加密
     * 修订记录：
     */
    public static class SHA256 {

        public static String encrypt(String data) {
            return CodecKit.encrypt(data, "SHA-256");
        }
    }

    /**
     * 作    者：wangr on 2017/5/31 16:39
     * 描    述：SHA384加密
     * 修订记录：
     */
    public static class SHA384 {

        public static String encrypt(String data) {
            return CodecKit.encrypt(data, "SHA-384");
        }

    }

    /**
     * 作    者：wangr on 2017/6/3 13:03
     * 描    述： BASE64编码解码
     * 修订记录：
     */
    public static class BASE64 {

        public static byte[] encode(byte[] plain) {
            return Base64.encode(plain, Base64.DEFAULT);
        }

        public static String encodeToString(byte[] plain) {
            return Base64.encodeToString(plain, Base64.DEFAULT);
        }

        public static byte[] decode(String text) {
            return Base64.decode(text, Base64.DEFAULT);
        }

        public static byte[] decode(byte[] text) {
            return Base64.decode(text, Base64.DEFAULT);
        }
    }

    /**
     * 作    者：wangr on 2017/5/31 17:18
     * 描    述：DES加密
     * 修订记录：
     */
    public static class DES {

        /**
         * 生成密钥
         *
         * @param seed
         * @return
         * @throws Exception
         */
        public static String initKey(@Nullable String seed) {
            String key = null;
            try {
                SecureRandom secureRandom = new SecureRandom(BASE64.decode(seed));
                KeyGenerator kg = KeyGenerator.getInstance("DES");
                kg.init(secureRandom);
                SecretKey secretKey = kg.generateKey();
                key = BASE64.encodeToString(secretKey.getEncoded());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return key;
        }

        /**
         * 转换秘钥
         *
         * @param key
         * @return
         * @throws Exception
         */
        private static Key toKey(byte[] key) {
            SecretKey secretKey = null;
            try {
                DESKeySpec dks = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                secretKey = keyFactory.generateSecret(dks);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return secretKey;
        }

        /**
         * 加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] encrypt(byte[] data, String key) {
            byte[] bytes = null;
            try {
                bytes = getCipher(Cipher.ENCRYPT_MODE, key).doFinal(data);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return bytes;
        }

        /**
         * 解密
         *
         * @param plain
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] decrypt(byte[] plain, String key) {
            byte[] bytes = null;
            try {
                bytes = getCipher(Cipher.DECRYPT_MODE, key).doFinal(plain);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return bytes;
        }

        /**
         * 生成Cipher
         *
         * @param opMode
         * @param key
         * @return
         */
        private static Cipher getCipher(int opMode, String key) {
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("DES");
                Key k = toKey(BASE64.decode(key));
                cipher.init(opMode, k);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return cipher;
        }

    }


    /**
     * 作    者：wangr on 2017/5/31 17:30
     * 描    述：RSA加密
     * 修订记录：
     */
    public static class RSA {

        public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
        private static final String PUBLIC_KEY = "RSAPublicKey";
        private static final String PRIVATE_KEY = "RSAPrivateKey";


        /**
         * 用私钥对信息生成数字签名
         *
         * @param data       加密数据
         * @param privateKey 私钥
         * @return
         * @throws Exception
         */
        public static String sign(byte[] data, String privateKey) {
            String sign = null;
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");// KEY_ALGORITHM 指定的加密算法
                byte[] keyBytes = BASE64.decode(privateKey);// 解密由base64编码的私钥
                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);// 构造PKCS8EncodedKeySpec对象
                PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);// 取私钥匙对象
                Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);// 用私钥对信息生成数字签名
                signature.initSign(priKey);
                signature.update(data);
                sign = BASE64.encodeToString(signature.sign());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return sign;
        }

        /**
         * 校验数字签名
         *
         * @param data      加密数据
         * @param publicKey 公钥
         * @param sign      数字签名
         * @return
         * @throws Exception
         */
        public static boolean verify(byte[] data, String publicKey, String sign) {
            boolean verify = false;
            try {
                byte[] keyBytes = BASE64.decode(publicKey); // 解密由base64编码的公钥
                KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // KEY_ALGORITHM 指定的加密算法
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  // 构造X509EncodedKeySpec对象
                PublicKey pubKey = keyFactory.generatePublic(keySpec);   // 取公钥对象
                Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
                signature.initVerify(pubKey);
                signature.update(data);
                verify = signature.verify(BASE64.decode(sign));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return verify;
        }

        /**
         * 用私钥解密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
            byte[] keyBytes = BASE64.decode(key);   // 对密钥解密

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   // 取得私钥
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        }

        /**
         * 用公钥解密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
            byte[] keyBytes = BASE64.decode(key);       // 对密钥解密

            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            // 对数据解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        }

        /**
         * 用公钥加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] encryptByPublicKey(byte[] data, String key)
                throws Exception {
            byte[] keyBytes = BASE64.decode(key);   // 对公钥解密

            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        }

        /**
         * 用私钥加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] encryptByPrivateKey(byte[] data, String key)
                throws Exception {

            byte[] keyBytes = BASE64.decode(key);   // 对密钥解密

            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        }

        /**
         * 取得私钥
         *
         * @param keyMap
         * @return
         * @throws Exception
         */
        public static String getPrivateKey(Map<String, Object> keyMap)
                throws Exception {
            Key key = (Key) keyMap.get(PRIVATE_KEY);

            return BASE64.encodeToString(key.getEncoded());
        }

        /**
         * 取得公钥
         *
         * @param keyMap
         * @return
         * @throws Exception
         */
        public static String getPublicKey(Map<String, Object> keyMap)
                throws Exception {
            Key key = (Key) keyMap.get(PUBLIC_KEY);

            return BASE64.encodeToString(key.getEncoded());
        }

        /**
         * 初始化密钥
         *
         * @return
         * @throws Exception
         */
        public static Map<String, Object> initKey() throws Exception {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);

            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();    // 公钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();     // 私钥
            Map<String, Object> keyMap = new HashMap<>(2);

            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        }

    }
}
