package ru.goldenapi.integrations;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class DefaultKafkaConfig {

    private Map<String, Object> properties = new HashMap<>();
    private String bootstrapServers;
    private String errorTopic;
    private Consumer groupConsumer;
    private Consumer specificConsumer;

    @Data
    public static class Consumer {

        private String groupId;
        private Long pollTimeout;
        private Integer concurrency;
        private String topic;
        private Map<String, Object> properties = new HashMap<>();
    }
}
