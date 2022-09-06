package com.kit.junit.provider.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kit.junit.annotation.JsonFileSource;
import com.kit.junit.enums.JsonMode;
import com.kit.junit.exception.KitJUnitException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Jackson
 *
 * @author Terry
 * @since 2022/3/26 10:52
 */
public class JacksonParser extends AbstractParser {

    public static final JacksonParser INSTANCE = new JacksonParser();

    @Override
    protected Object readObject(InputStream inputStream, Charset charset, JsonFileSource annotation, Type type) {
        ObjectMapper jsonParser = choose(annotation);
        InputStreamReader reader = new InputStreamReader(inputStream, charset);
        try {
            JavaType javaType = jsonParser.constructType(type);
            return jsonParser.readValue(reader, javaType);
        } catch (Exception e) {
            throw new KitJUnitException("jackson readObject error", e);
        }
    }

    private ObjectMapper choose(JsonFileSource annotation) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // 配置忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (annotation.jsonMode() == JsonMode.UNDER_LINE) {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        }
        return objectMapper;
    }

}
