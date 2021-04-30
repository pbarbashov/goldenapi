package ru.goldenapi.marshall;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonMapperFactory {
    public static ObjectMapper create() {
        var om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return om;
    }
}
