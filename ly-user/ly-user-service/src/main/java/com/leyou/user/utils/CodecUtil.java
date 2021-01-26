package com.leyou.user.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @Author: qyl
 * @Date: 2021/1/25 11:32
 */
public class CodecUtil {

    public static String md5Hex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    public static String generateSalt() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
