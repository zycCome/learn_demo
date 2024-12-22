package com.zyc.signature;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 各种签名算法测试
 *
 * @author zhuyc
 * @date 2021/11/23 10:55
 */
public class SignPerformanceTest {

    /**
     * 生成一个包含多行随机字符串的文件，保证签名时的数据是稳定不变的
     */
    @Test
    public void generateRandomStrTxt() throws IOException {
        File file = new File("d:/randomStr.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        int rows = 50000;
        int strLength = 2000;
        List<String> list = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            list.add(getRandomString(strLength));
        }
        System.out.println("size:" + GraphLayout.parseInstance(list).totalSize());
        FileWriter writer = new FileWriter(file);
        writer.writeLines(list);
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    /**
     * signByMd5--rows=50000,elapse=470
     * signByShA1--rows=50000,elapse=607
     * signBySHA256--rows=50000,elapse=786
     */
    @Test
    public void testSign() {
        File file = new File("d:/randomStr.txt");
        FileReader fileReader = new FileReader(file);
        List<String> list = fileReader.readLines();
        //预热的行数
        int preheatRow = 0;
        String secret = "7KpBtCxFAymJxpwt";
        signByMd5(list, preheatRow, secret);
        signBySHA1(list, preheatRow, secret);
        signBySHA256(list, preheatRow, secret);
        signByRSA(list, preheatRow ,true);
        signByRSA(list, preheatRow ,false);
    }

    /**
     * 用md5签名
     *
     * @param list       需要签名的字符串集合
     * @param preheatRow 预热阶段的行数
     * @param secret     密钥
     */
    private void signByMd5(List<String> list, int preheatRow, String secret) {
        byte[] bytes = secret.getBytes();
        HMac hmac = SecureUtil.hmac(HmacAlgorithm.HmacMD5, bytes);
        long start = prehetAndRun(list, preheatRow, hmac);
        System.out.println("signByMd5--rows=" + list.size() + ",elapse=" + (System.currentTimeMillis() - start));
    }

    /**
     * 预热并且执行签名计算
     *
     * @param list
     * @param preheatRow
     * @param hmac
     * @return
     */
    private long prehetAndRun(List<String> list, int preheatRow, HMac hmac) {
        for (int i = 0; i < preheatRow; i++) {
            hmac.digestHex(list.get(i));
        }
        long start = System.currentTimeMillis();
        for (String s : list) {
            hmac.digestHex(s);
        }
        return start;
    }

    private void signBySHA1(List<String> list, int preheatRow, String secret) {
        byte[] bytes = secret.getBytes();
        HMac hmac = SecureUtil.hmac(HmacAlgorithm.HmacSHA1, bytes);
        long start = prehetAndRun(list, preheatRow, hmac);
        System.out.println("signByShA1--rows=" + list.size() + ",elapse=" + (System.currentTimeMillis() - start));
    }

    private void signBySHA256(List<String> list, int preheatRow, String secret) {
        byte[] bytes = secret.getBytes();
        HMac hmac = SecureUtil.hmac(HmacAlgorithm.HmacSHA256, bytes);
        long start = prehetAndRun(list, preheatRow, hmac);
        System.out.println("signBySHA256--rows=" + list.size() + ",elapse=" + (System.currentTimeMillis() - start));
    }

    private void signByRSA(List<String> list, int preheatRow, boolean paralle) {
        RSA rsa = new RSA();
        //获得私钥
        rsa.getPrivateKey();
        rsa.getPrivateKeyBase64();
        //获得公钥
        rsa.getPublicKey();
        rsa.getPublicKeyBase64();
        long start = System.currentTimeMillis();
        List<String> newList = new ArrayList<>(list.subList(0, preheatRow));
        if (paralle) {
            newList.parallelStream().forEach(str -> {
                RSA rsa2 = new RSA();
//                byte[] encrypt = rsa2.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
//                byte[] decrypt = rsa2.decrypt(encrypt, KeyType.PrivateKey);


                //私钥加密，公钥解密
                byte[] encrypt2 = rsa2.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
                byte[] decrypt2 = rsa2.decrypt(encrypt2, KeyType.PublicKey);
            });
        } else {
            newList.stream().forEach(str -> {
//                byte[] encrypt = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
//                byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);


                //私钥加密，公钥解密
                byte[] encrypt2 = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
                byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
            });
        }
        System.out.println("signByRSA--rows=" + list.size() + ",elapse=" + (System.currentTimeMillis() - start));
    }


}
