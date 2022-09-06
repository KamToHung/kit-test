package com.kit.junit.annotation;


import com.kit.junit.enums.JsonMode;
import com.kit.junit.enums.Type;
import com.kit.junit.provider.JsonFileArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/**
 * Json
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 21:34
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(JsonFileArgumentsProvider.class)
public @interface JsonFileSource {

    /**
     * json classpath resources
     */
    String[] resources() default {};

    /**
     * json files
     */
    String[] files() default {};

    /**
     * resource directory
     */
    String[] resourceDirs() default {};

    /**
     * file directory
     */
    String[] fileDirs() default {};

    /**
     * encoding
     */
    String encoding() default "UTF-8";

    /**
     * under_line or camel
     */
    JsonMode jsonMode() default JsonMode.CAMEL;

    /**
     * type
     */
    Type type() default Type.GSON;

}
