package com.kit.junit.provider;

import com.kit.junit.annotation.JsonFileSource;
import com.kit.junit.provider.parser.ParserFactory;
import com.kit.junit.provider.source.CollectionSource;
import com.kit.junit.provider.source.Source;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * json provider
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 * @since 2022/3/24 20:50
 */
public class JsonFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonFileSource> {

    private final InputStreamProvider inputStreamProvider;

    private List<Source> sources;

    private List<CollectionSource> collectionSources;

    private Charset charset;

    private ParserExecutor parserExecutor;

    public JsonFileArgumentsProvider() {
        this(DefaultInputStreamProvider.INSTANCE);
    }

    public JsonFileArgumentsProvider(final InputStreamProvider inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    @Override
    public void accept(final JsonFileSource annotation) {
        Stream<Source> resources = Arrays.stream(annotation.resources()).map(inputStreamProvider::classpathResource);
        Stream<Source> files = Arrays.stream(annotation.files()).map(inputStreamProvider::file);
        Stream<CollectionSource> collectionSources = Arrays.stream(annotation.resourceDirs()).map(inputStreamProvider::classpathResourceDirectory);
        Stream<CollectionSource> collectionFiles = Arrays.stream(annotation.fileDirs()).map(inputStreamProvider::classpathFileDirectory);
        this.sources = Stream.concat(resources, files).collect(Collectors.toList());
        this.collectionSources = Stream.concat(collectionSources, collectionFiles).collect(Collectors.toList());
        this.charset = getCharsetFrom(annotation);
        this.parserExecutor = ParserFactory.create(annotation.type()).objectMapper(annotation);
    }

    private Charset getCharsetFrom(JsonFileSource annotation) {
        return Charset.forName(annotation.encoding());
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return Stream.concat(
                this.sources.stream().map(source -> source.open(context)),
                this.collectionSources.stream().map(source -> source.open(context)).flatMap(Collection::stream))
            .map(reader -> this.parserExecutor.beginParsing(context, reader, this.charset))
            .flatMap(this::toStream);
    }

    private Stream<Arguments> toStream(final Object o) {
        return Stream.of(Arguments.of(o));
    }

}
