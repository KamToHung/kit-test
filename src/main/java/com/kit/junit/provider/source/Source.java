package com.kit.junit.provider.source;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.InputStream;

/**
 * 资源
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 20:50
 */
@FunctionalInterface
public interface Source {

    InputStream open(ExtensionContext context);

}
