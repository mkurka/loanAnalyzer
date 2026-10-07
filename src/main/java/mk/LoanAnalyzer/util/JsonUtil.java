package mk.LoanAnalyzer.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.time.LocalDateTime;

/**
 * TODO Make me more handy, please, please :-/
 *
 * Created by fja on 13.10.2015.
 */
public class JsonUtil {

    /**
     *
     * @param sb
     * @param propertyName
     * @param value
     * @return The same StringBuilder for chaining.
     */
    public static StringBuilder appendPropetyValue(StringBuilder sb, String propertyName, Object value) {
        return sb.append('"').append(propertyName).append("\": \"").append(value).append('"');
    }

    /**
     * Also starts object with curly bracket.
     * @param propertyName
     * @param value
     * @return New StringBuilder for chaining.
     */
    public static StringBuilder appendFirstPropetyValue(String propertyName, Object value) {
        return appendPropetyValue(new StringBuilder("{ "), propertyName, value);
    }

    /**
     *
     * @param sb
     * @return The same StringBuilder for chaining.
     */
    public static StringBuilder appendEnd(StringBuilder sb) {
        return sb.append(" }");
    }

    public static <T> T[] deserializeArray(Class<T> clazz, String jsonString)throws IOException {
        return deserializeArray(clazz, jsonString, true);
    }

    public static <T> T[] deserializeArray(Class<T> clazz, String jsonString, boolean failOnUnknownProperties)throws IOException {
        var arrayClazz = Array.newInstance(clazz, 0).getClass();
        return (T[]) deserialize(arrayClazz, jsonString, failOnUnknownProperties);
    }

    public static <T> T deserialize(Class<T> clazz, String jsonString) throws IOException {
        return deserialize(clazz, jsonString, true);
    }

    public static <T> T deserialize(Class<T> clazz, String jsonString, boolean failOnUnknownProperties) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new SimpleModule("JavaTimeModuleHack",
                new Version(0, 1, 0, null, "esport", "JavaTimeModuleHack"))
                .addSerializer(LocalDateTime.class, ZuluLocalDateTimeSerializer.INSTANCE));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties); // Because payload is much more richer, but we want only very limited subset of its elements.
        return objectMapper.readValue(jsonString, clazz);
    }
    public static <T> T deserialize(Class<T> clazz, JsonNode node) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.treeToValue(node, clazz);
        return deserialize(clazz, node, true);
    }

    public static <T> T deserialize(Class<T> clazz, JsonNode node, boolean failOnUnknownProperties) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties); // Because payload is much more richer, but we want only very limited subset of its elements.
        return objectMapper.treeToValue(node, clazz);
    }

    public static <T> void serializeToFile(T value, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new SimpleModule("JavaTimeModuleHack",
                new Version(0, 1, 0, null, "esport", "JavaTimeModuleHack"))
                .addSerializer(LocalDateTime.class, ZuluLocalDateTimeSerializer.INSTANCE));
        mapper.writeValue(file, value);

    }

    public static <T> String serializeToString(T value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new SimpleModule("JavaTimeModuleHack",
                new Version(0, 1, 0, null, "esport", "JavaTimeModuleHack"))
                .addSerializer(LocalDateTime.class, ZuluLocalDateTimeSerializer.INSTANCE));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        StringWriter sw = new StringWriter();
        mapper.writeValue(sw, value);
        return sw.toString();
    }

}
