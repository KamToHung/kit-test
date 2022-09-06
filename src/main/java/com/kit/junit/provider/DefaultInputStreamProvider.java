package com.kit.junit.provider;


import com.kit.junit.exception.KitJUnitException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 默认Provider
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 15:43
 */
public class DefaultInputStreamProvider implements InputStreamProvider {

    public static final DefaultInputStreamProvider INSTANCE = new DefaultInputStreamProvider();

    @Override
    public InputStream openClasspathResource(Class<?> baseClass, String path) {
        return baseClass.getResourceAsStream(path);
    }

    @Override
    public InputStream openFile(String path) {
        try {
            return Files.newInputStream(Paths.get(path));
        } catch (IOException e) {
            throw new KitJUnitException("File [" + path + "] could not be read", e);
        }
    }

    @Override
    public Collection<InputStream> openClasspathResourceDirectory(Class<?> baseClass, String path) {
        URL url = baseClass.getResource(path);
        if (url == null) {
            throw new KitJUnitException("Directory [" + path + "] could not be read");
        }
        File[] files = new File(url.getPath()).listFiles();
        if (files == null || files.length == 0) {
            throw new KitJUnitException("Directory [" + path + "] could not found files");
        }
        Collection<InputStream> result = new ArrayList<>();
        try {
            for (File file : files) {
                result.add(Files.newInputStream(file.toPath()));
            }
        } catch (Exception e) {
            throw new KitJUnitException("not file found!", e);
        }
        return result;
    }

    @Override
    public Collection<InputStream> openClasspathFileDirectory(Class<?> requiredTestClass, String path) {
        try {
            DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get(path));
            Collection<InputStream> result = new ArrayList<>();
            for (Path p : paths) {
                result.add(Files.newInputStream(p));
            }
            return result;
        } catch (IOException e) {
            throw new KitJUnitException("Directory [" + path + "] could not be read", e);
        }
    }

}
