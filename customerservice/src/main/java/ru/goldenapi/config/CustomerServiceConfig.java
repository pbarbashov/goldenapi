package ru.goldenapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.goldenapi.marshall.JacksonMapperFactory;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "service")
@Data
public class CustomerServiceConfig {

    @Bean(name = "goldenObjectMapper")
    public ObjectMapper objectMapper(){
        return JacksonMapperFactory.create();
    }
}
