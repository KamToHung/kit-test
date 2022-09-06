package com.kit.junit.provider.parser;

import com.kit.junit.enums.Type;

/**
 * 工厂
 *
 * @author Terry
 * @since 2022/3/26 11:21
 */
public class ParserFactory {

    public static AbstractParser create(Type type) {
        switch (type) {
            case JACKSON:
                return JacksonParser.INSTANCE;
            case FASTJSON:
                return FastjsonParser.INSTANCE;
            case GSON:
            default:
                return GsonParser.INSTANCE;
        }
    }

}
