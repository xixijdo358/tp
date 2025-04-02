package com.example.utils;

/**功能描述：工具类
 */
public class XuedenUtil {

    /**
     * 随机生成六位数
     * 该方法使用Math.random()生成一个随机数，并将其转换为六位数的整数。
     * @return 返回生成的六位随机数
     */
   public static int randomSixNums(){
       // 生成一个1到9之间的随机数，并乘以100000，确保结果为六位数
       int code = (int) ((Math.random() * 9 + 1) * 100000);
       return code; // 返回生成的六位数
   }

    public static void main(String[] args) {
        // 输出生成的六位随机数
        System.out.println(XuedenUtil.randomSixNums());
    }
}
