package xyz.yangzhe.crowd.util;

import sun.net.httpserver.HttpServerImpl;
import xyz.yangzhe.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: 通用工具方法类
 * @Author: Yangzhe
 * @Data: 2020/6/12
 */
public class CrowdUtil {

    /**
     * @Description: 判断当前请求是否为请求json数据的ajax请求
     * @Return: true -> 是;   false -> 否
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1. 获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        // 2. 返回判断结果
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
    }

    /**
     * @Description: 对密码明文进行md5加密
     * @Return: 加密后的结果
     */
    public static String md5(String source){
        // 1. 判断source是否有效
        if(source == null || source.length() == 0){
            // 2. 如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            // 3. 获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 4. 获取明文字符串对应的字节数组
            byte[] input = source.getBytes();
            // 5. 执行加密
            byte[] output = messageDigest.digest(input);
            // 6. 创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum,output);
            // 7. 按照16进制将bigInteger的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

}
