package ru.goldenapi.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.goldenapi.integrations.DefaultKafkaConfig;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "kafka")
@Primary
@Data
@EqualsAndHashCode(callSuper = true)
public class KafkaConfig extends DefaultKafkaConfig {

}
