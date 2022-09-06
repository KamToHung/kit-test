package com.kit.junit.provider.parser;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.kit.junit.annotation.JsonFileSource;
import com.kit.junit.enums.JsonMode;
import com.kit.junit.exception.KitJUnitException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * Gson
 *
 * @author Terry
 * @since 2022/3/26 10:52
 */
public class GsonParser extends AbstractParser {

    public static final GsonParser INSTANCE = new GsonParser();

    @Override
    protected Object readObject(InputStream inputStream, Charset charset, JsonFileSource annotation, Type type) {
        Gson jsonParser = choose(annotation);
        InputStreamReader reader = new InputStreamReader(inputStream, charset);
        return jsonParser.fromJson(reader, type);
    }

    private Gson choose(JsonFileSource annotation) {
        if (annotation.jsonMode() == JsonMode.UNDER_LINE) {
            return Parser.UNDERSCORE_GSON;
        }
        return Parser.GSON;
    }

    private static class Parser {
        private abstract static class DateTimeAdapter<T> extends TypeAdapter<T> {

            static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

            static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );

            abstract T from(Long time);

            abstract T from(TemporalAccessor temporalAccessor);

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(value.toString());
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonToken token = in.peek();
                if (token == JsonToken.NUMBER) {
                    return from(in.nextLong());
                } else if (token == JsonToken.STRING) {
                    String time = in.nextString();
                    for (DateTimeFormatter formatter : FORMATTERS) {
                        try {
                            TemporalAccessor temporalAccessor = formatter.parse(time);
                            return from(temporalAccessor);
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                } else if (token == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                throw new KitJUnitException("Unsupported date time format: " + in.nextString());
            }

        }

        private static final class DateAdapter extends DateTimeAdapter<Date> {

            @Override
            Date from(Long time) {
                return new Date(time);
            }

            @Override
            Date from(TemporalAccessor temporalAccessor) {
                return Date.from(Instant.from(temporalAccessor));
            }

            @Override
            public void write(JsonWriter out, Date value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(FORMATTER.format(ZonedDateTime.from(value.toInstant().atZone(ZoneId.systemDefault()))));
            }
        }

        private static final class ZonedDateTimeAdapter extends DateTimeAdapter<ZonedDateTime> {

            @Override
            ZonedDateTime from(Long time) {
                return ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
            }

            @Override
            ZonedDateTime from(TemporalAccessor temporalAccessor) {
                return ZonedDateTime.from(temporalAccessor);
            }

        }

        private static final class LocalDateTimeAdapter extends DateTimeAdapter<LocalDateTime> {

            @Override
            LocalDateTime from(Long time) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
            }

            @Override
            LocalDateTime from(TemporalAccessor temporalAccessor) {
                return LocalDateTime.from(temporalAccessor);
            }

        }

        private static final class LocalDateAdapter extends DateTimeAdapter<LocalDate> {

            @Override
            LocalDate from(Long time) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).toLocalDate();
            }

            @Override
            LocalDate from(TemporalAccessor temporalAccessor) {
                return LocalDate.from(temporalAccessor);
            }
        }

        public static final class CalendarAdapter extends DateTimeAdapter<Calendar> {

            @Override
            Calendar from(Long time) {
                return GregorianCalendar.from(ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
            }

            @Override
            Calendar from(TemporalAccessor temporalAccessor) {
                return GregorianCalendar.from(ZonedDateTime.from(temporalAccessor));
            }

        }

        public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeHierarchyAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .registerTypeAdapter(Date.class, new DateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeHierarchyAdapter(Calendar.class, new CalendarAdapter())
            .create();

        public static final Gson UNDERSCORE_GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeHierarchyAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .registerTypeAdapter(Date.class, new DateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeHierarchyAdapter(Calendar.class, new CalendarAdapter())
            .create();

    }

}
