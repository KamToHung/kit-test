package com.kit.junit.provider.source;

import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.InputStream;
import java.util.Collection;

/**
 * 资源
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 20:50
 */
@FunctionalInterface
public interface CollectionSource {

    Collection<InputStream> open(ExtensionContext context);

}
