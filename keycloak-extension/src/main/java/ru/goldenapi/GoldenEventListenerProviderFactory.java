package ru.goldenapi;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import ru.goldenapi.dto.TransportMessage;
import ru.goldenapi.marshall.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class GoldenEventListenerProviderFactory implements EventListenerProviderFactory {
    private KafkaSender<String, TransportMessage> kafkaSender;
    private String topic;

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new GoldenKeycloakEventListener(kafkaSender, topic, keycloakSession);
    }

    @Override
    public void init(Config.Scope conf) {
        Map<String, Object> producerProps = new HashMap<>();
        //addProp(producerProps,conf, ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.get("bootstrapServers","broker:29092"));
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //addProp(producerProps,conf, ProducerConfig.BUFFER_MEMORY_CONFIG);
        topic = conf.get("outTopic","keycloak");
        var senderOptions = SenderOptions.<String,TransportMessage>create(producerProps)
                .maxInFlight(conf.getInt("maxInFlight",1024));
        kafkaSender = KafkaSender.create(senderOptions);
    }

    private void addProp(Map<String, Object> producerProps, Config.Scope conf, String key) {
        producerProps.put(key,conf.get(key));
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {
        kafkaSender.close();
    }

    @Override
    public String getId() {
        return "kafka-event-processor";
    }
}
