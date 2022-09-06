package com.kit.junit.provider.parser;

import com.kit.junit.annotation.JsonFileSource;
import com.kit.junit.exception.KitJUnitException;
import com.kit.junit.provider.ParserExecutor;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

/**
 * abstract parserProvider
 *
 * @author Terry
 * @since 2022/3/26 11:36
 */
public abstract class AbstractParser {

    Object parser(ExtensionContext context, InputStream inputStream, Charset charset, JsonFileSource annotation) {
        checkParam(context);
        return getObject(context, inputStream, charset, annotation);
    }

    public ParserExecutor objectMapper(JsonFileSource annotation) {
        return (context, inputStream, charset) -> parser(context, inputStream, charset, annotation);
    }

    private void checkParam(ExtensionContext context) {
        Parameter[] parameters = context.getRequiredTestMethod().getParameters();
        if (parameters.length != 1) {
            throw new KitJUnitException("Exactly one type of input must be provided in the @"
                + JsonFileSource.class.getSimpleName() + " annotation, but there were " + parameters.length);
        }
    }

    private Class<?> getClass(ExtensionContext context) {
        return context.getRequiredTestMethod().getParameterTypes()[0];
    }

    private Type getType(ExtensionContext context) {
        return context.getRequiredTestMethod().getParameters()[0].getParameterizedType();
    }

    private Object getObject(ExtensionContext context, InputStream inputStream, Charset charset, JsonFileSource annotation) {
        Type type = getType(context);
        Class<?> cls = getClass(context);
        if (String.class.isAssignableFrom(cls)) {
            return new BufferedReader(new InputStreamReader(inputStream, charset)).lines().collect(Collectors.joining());
        }
        return readObject(inputStream, charset, annotation, type);
    }

    protected abstract Object readObject(InputStream inputStream, Charset charset, JsonFileSource annotation, Type type);

}
