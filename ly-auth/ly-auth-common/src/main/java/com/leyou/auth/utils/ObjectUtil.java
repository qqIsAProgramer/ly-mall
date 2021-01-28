package com.leyou.auth.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: qyl
 * @Date: 2021/1/28 16:38
 *
 * 从jwt解析得到的数据是Object类型，转化为具体类型可能出现空指针
 * 在这个工具类进行一些转换
 */
public class ObjectUtil {

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Double || obj instanceof Float) {
            return Long.valueOf(StringUtils.substringBefore(obj.toString(), "."));
        }
        if (obj instanceof Number) {
            return Long.valueOf(obj.toString());
        }
        if (obj instanceof String) {
            return Long.valueOf(obj.toString());
        }
        return 0L;
    }
}
