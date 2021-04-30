package ru.goldenapi;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import reactor.kafka.sender.SenderOptions;
import ru.goldenapi.marshall.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class GoldenEventListenerProviderFactory implements EventListenerProviderFactory {

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {

        return new GoldenKeycloakEventListener();
    }

    @Override
    public void init(Config.Scope conf) {
        Map<String, Object> producerProps = new HashMap<>();
        addProp(producerProps,conf, ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        addProp(producerProps,conf, ProducerConfig.BUFFER_MEMORY_CONFIG);

        SenderOptions<Integer,String> senderOptions = SenderOptions.<Integer,String>create(producerProps)
                .maxInFlight(conf.getInt("maxInFlight",1024));


    }

    private void addProp(Map<String, Object> producerProps, Config.Scope conf, String key) {
        producerProps.put(key,conf.get(key));
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "kafka-event-listener";
    }
}
