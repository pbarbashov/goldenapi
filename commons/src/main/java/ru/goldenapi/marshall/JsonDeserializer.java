package ru.goldenapi.marshall;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import ru.goldenapi.GoldHeaders;
import ru.goldenapi.exception.FailMessageDto;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j

public class JsonDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper om = JacksonMapperFactory.create();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public T deserialize(String topic, byte[] data) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(String topic, Headers headers, byte[] data) {
        if (data == null) {
            log.warn("Null object for deserialization from kafka!");
            return null;
        }
        T obj;

        try {
            Iterator<Header> javaTypeItr = headers.headers(GoldHeaders.TYPE.name()).iterator();

            if (javaTypeItr.hasNext()) {
                String javaType = new String(javaTypeItr.next().value(), StandardCharsets.UTF_8);
                obj = om.readerFor(om.getTypeFactory().constructFromCanonical(javaType)).readValue(data);
            } else {
                throw new NoSuchElementException("No type information in headers");
            }
        } catch (Exception e) {
            String body = new String(data, StandardCharsets.UTF_8);
            log.error(String.format("Can't deserialize data [%s] from topic %s",
                    Arrays.toString(data), topic));
            return (T) new FailMessageDto(body,
                    new SerializationException(String.format("Can't deserialize data [%s] from topic %s",
                    Arrays.toString(data), topic),e));
        }

        return obj;
    }

    @Override
    public void close() {

    }
}
