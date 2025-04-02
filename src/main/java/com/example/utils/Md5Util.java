package com.example.utils;

import java.security.MessageDigest;

/**功能描述：MD5加密工具类
 * 该类提供了MD5加密的方法，可以将输入的字符串转换为MD5加密后的十六进制字符串。
 */
public class Md5Util {
    /**
     * MD5加密方法
     * @param s 需要加密的字符串
     * @return 返回加密后的十六进制字符串
     */
    public final static String MD5(String s) {
        // 定义十六进制字符数组，用于将字节转换为十六进制字符串
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            // 将输入字符串转换为字节数组
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                // 将每个字节转换为两个十六进制字符
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            // 返回转换后的十六进制字符串
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常信息
            return null; // 返回null表示加密失败
        }
    }

    public static void main(String[] args) {
        // 测试MD5加密方法，输出"123456"的MD5加密结果
        System.out.printf(Md5Util.MD5("123456"));
    }
}
