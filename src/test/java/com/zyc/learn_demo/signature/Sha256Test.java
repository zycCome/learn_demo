package com.zyc.learn_demo.signature;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.junit.Test;


/**
 * @author zhuyc
 * @date 2021/11/2 10:31
 */
public class Sha256Test {

    /**
     * 测试生成签名
     */
    @Test
    public void test1() {
        String appId = "1111";
        String appSecret = "asdasdasdas";
        long timeStamp = System.currentTimeMillis();
        String sign = createSign(getStringToSign("GET", "/test/getDevice?id=1", timeStamp, ""), appSecret);
        System.out.println(sign);

        //服务端用一样的算法校验签名即可
        System.out.println(checkSign(getStringToSign("GET", "/test/getDevice?id=1", timeStamp, ""), appSecret, sign));

    }


    /**
     * 比较签名
     *
     * @param signTemp
     * @param appSecret
     * @param requestSign
     * @return
     */
    private boolean checkSign(String signTemp, String appSecret, String requestSign) {
        String sign = createSign(signTemp, appSecret);
        return sign.equals(requestSign);
    }


    /**
     *
     * @param url
     * @param timeStamp
     * @return
     */

    /**
     * 生成待签名字符串
     *
     * @param method    请求方式，如GET、POST、DELETE、PUT
     * @param url       获取请求的绝对URL，并去除域名部分得到参与签名的URL。如果请求中有查询参数，URL末尾应附加有'?'和对应的查询字符串。
     * @param timeStamp 时间戳，单位毫秒
     * @param body      请求体
     * @return
     */
    public String getStringToSign(String method, String url, long timeStamp, String body) {
        StringBuilder toSignBuilder = new StringBuilder();
        toSignBuilder
                .append(method).append("\n")
                .append(url).append("\n")
                .append(timeStamp).append("\n")
                .append(body).append("\n");
        return toSignBuilder.toString();
    }


    /**
     * 生成签名
     *
     * @param signTemp 需要签名的字符串
     * @param secret   密钥
     * @return 签名后的数据
     */
    public static String createSign(String signTemp, String secret) {
        return SecureUtil.hmac(HmacAlgorithm.HmacSHA256, secret).digestHex(signTemp).toUpperCase();
    }
}
