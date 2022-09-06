package com.kit.junit.provider;

import com.kit.junit.provider.source.CollectionSource;
import com.kit.junit.provider.source.Source;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.InputStream;
import java.util.Collection;

/**
 * provider
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 20:49
 */
public interface InputStreamProvider {

    InputStream openClasspathResource(Class<?> baseClass, String path);

    InputStream openFile(String path);

    Collection<InputStream> openClasspathResourceDirectory(Class<?> baseClass, String path);

    Collection<InputStream> openClasspathFileDirectory(Class<?> requiredTestClass, String path);

    default Source classpathResource(String path) {
        return context -> openClasspathResource(context.getRequiredTestClass(), path);
    }

    default CollectionSource classpathResourceDirectory(String path) {
        return context -> openClasspathResourceDirectory(context.getRequiredTestClass(), path);
    }

    default Source file(String path) {
        return context -> openFile(path);
    }

    default CollectionSource classpathFileDirectory(String path) {
        return context -> openClasspathFileDirectory(context.getRequiredTestClass(), path);
    }

}
