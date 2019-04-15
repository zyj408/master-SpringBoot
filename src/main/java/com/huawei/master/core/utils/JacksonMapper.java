package com.huawei.master.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonMapper {
    /**
     * 对象映射
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     */
    private JacksonMapper() {

    }

    /**
     * @return
     */
    public static ObjectMapper getInstance() {

        return mapper;
    }

    public static String serialize(Object object)
    {
        try {
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }

    }

    public static <T> T deserialize(String string, Class<T> clazz)
    {
        try {
            return getInstance().readValue(string, clazz);
        } catch (IOException e) {
            return null;
        }

    }
}
