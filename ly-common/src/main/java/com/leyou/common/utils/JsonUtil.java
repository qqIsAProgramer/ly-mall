package com.leyou.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: qyl
 * @Date: 2021/1/24 16:55
 */
public class JsonUtil {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String serialize(Object object) {
        if (object == null) {
            return null;
        }
        if (object.getClass() == String.class) {
            return (String) object;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + object, e);
            return null;
        }
    }

    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }
}
