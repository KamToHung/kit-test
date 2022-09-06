package com.kit.mock.annotation;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.env.Environment;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableMockDubbo
public @interface MockDubboSpringBootTest {

    /**
     * Alias for {@link #properties()}.
     *
     * @return the properties to apply
     */
    @AliasFor(value = "value", annotation = SpringBootTest.class)
    String[] value() default {};

    /**
     * Properties in form {@literal key=value} that should be added to the Spring
     * {@link Environment} before the test runs.
     *
     * @return the properties to add
     */
    @AliasFor(value = "properties", annotation = SpringBootTest.class)
    String[] properties() default {};

    /**
     * Application arguments that should be passed to the application under test.
     *
     * @return the application arguments to pass to the application under test.
     * @see ApplicationArguments
     * @see SpringApplication#run(String...)
     * @since 2.2.0
     */
    @AliasFor(value = "args", annotation = SpringBootTest.class)
    String[] args() default {};

    /**
     * The <em>component classes</em> to use for loading an
     * {@link org.springframework.context.ApplicationContext ApplicationContext}. Can also
     * be specified using
     * {@link ContextConfiguration#classes() @ContextConfiguration(classes=...)}. If no
     * explicit classes are defined the test will look for nested
     * {@link Configuration @Configuration} classes, before falling back to a
     * {@link SpringBootConfiguration @SpringBootConfiguration} search.
     *
     * @return the component classes used to load the application context
     * @see ContextConfiguration#classes()
     */
    @AliasFor(value = "classes", annotation = SpringBootTest.class)
    Class<?>[] classes() default {};

    /**
     * The type of web environment to create when applicable. Defaults to
     * {@link SpringBootTest.WebEnvironment#MOCK}.
     *
     * @return the type of web environment
     */
    @AliasFor(value = "webEnvironment", annotation = SpringBootTest.class)
    SpringBootTest.WebEnvironment webEnvironment() default SpringBootTest.WebEnvironment.MOCK;

}
