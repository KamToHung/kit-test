package com.kit.junit.provider;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.InputStream;
import java.nio.charset.Charset;

@FunctionalInterface
public interface ParserExecutor {

    Object beginParsing(ExtensionContext context, InputStream inputStream, Charset charset);

}
