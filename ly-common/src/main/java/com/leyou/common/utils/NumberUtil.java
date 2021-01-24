package com.leyou.common.utils;

import java.util.Random;

/**
 * @Author: qyl
 * @Date: 2021/1/24 16:30
 */
public class NumberUtil {

    /**
     * 生成指定位数的随机数字
     * @param len 随机数字的位数（最大为8）
     * @return 随机数字
     */
    public static String generateCode(int len) {
        len = Math.min(len, 8);  // eg: len = 6
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();  // min = 100000
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue()) + min;  // [0, 9999999] + 100000
        return String.valueOf(num).substring(0, len);
    }
}
