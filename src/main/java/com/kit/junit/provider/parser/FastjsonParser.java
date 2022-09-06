package com.kit.junit.provider.parser;

import com.alibaba.fastjson.JSON;
import com.kit.junit.annotation.JsonFileSource;
import com.kit.junit.exception.KitJUnitException;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Fastjson
 *
 * @author Terry
 * @since 2022/3/26 10:52
 */
public class FastjsonParser extends AbstractParser {

    public static final FastjsonParser INSTANCE = new FastjsonParser();

    @Override
    protected Object readObject(InputStream inputStream, Charset charset, JsonFileSource annotation, Type type) {
        try {
            return JSON.parseObject(inputStream, charset, type);
        } catch (Exception e) {
            throw new KitJUnitException("fastjson readCollection error", e);
        }
    }
}
