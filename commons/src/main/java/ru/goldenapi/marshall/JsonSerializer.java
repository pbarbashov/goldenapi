package ru.goldenapi.marshall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import ru.goldenapi.GoldHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class JsonSerializer<T> implements Serializer<T> {
    private final ObjectMapper om = JacksonMapperFactory.create();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            log.warn("Null object for serialization to kafka!");
            return null;
        }
        try {
            return om.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.warn("Error on serialization of object " + data);
            throw new SerializationException("Error on serialization of object",e);
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, T data) {
        if (data == null) {
            log.warn("Null object for serialization to kafka!");
            return null;
        }
        JavaType javaType = om.constructType(data.getClass());
        headers.add(GoldHeaders.TYPE.name(), javaType.getRawClass().getName().getBytes(StandardCharsets.UTF_8));
        return serialize(topic,data);
    }

    @Override
    public void close() {/*Nothing to do*/}
}
