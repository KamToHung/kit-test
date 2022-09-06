package com.kit.mock.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MockDubboSelector.class)
public @interface EnableMockDubbo {
}
