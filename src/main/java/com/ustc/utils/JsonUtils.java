package com.ustc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Json转换工具
 * @author 叶嘉耘
 */
@Component
public class JsonUtils {
    private static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    /**
     * 将对象转换为Json字符串
     *
     * @param data 数据
     * @return 对应的Json字符串
     */
    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果转换为对象
     *
     * @param jsonData json数据
     * @param beanType 对象类型
     * @return 返回对象
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
