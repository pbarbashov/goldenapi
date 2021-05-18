package ru.goldenapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import ru.goldenapi.dto.TransportMessage;
import ru.goldenapi.marshall.JsonDeserializer;
import ru.goldenapi.marshall.JsonSerializer;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@Slf4j
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaConfig kafkaConfig;

    @Bean
    public KafkaSender<String, TransportMessage> kafkaSender() {
        return KafkaSender.create(producerProps());
    }

    @NotNull
    private SenderOptions<String, TransportMessage> producerProps() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaConfig.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //producerProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"customerservice_tx_str"); -- for transactional

        if (!this.kafkaConfig.getProperties().isEmpty()) {
            producerProps.putAll(this.kafkaConfig.getProperties());
        }
        return SenderOptions.create(producerProps);
    }

    @NotNull
    private ReceiverOptions<String, TransportMessage> groupConsumerProps(){
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaConfig.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaConfig.getGroupConsumer().getGroupId());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        if (!this.kafkaConfig.getProperties().isEmpty()) {
            consumerProps.putAll(this.kafkaConfig.getProperties());
        }

        return ReceiverOptions.<String,TransportMessage>create(consumerProps)
                .subscription(Collections.singleton(this.kafkaConfig.getGroupConsumer().getTopic()));
    }

    @NotNull
    private ReceiverOptions<String, TransportMessage> specificConsumerProps(){
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaConfig.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaConfig.getSpecificConsumer().getGroupId());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        if (!this.kafkaConfig.getProperties().isEmpty()) {
            consumerProps.putAll(this.kafkaConfig.getProperties());
        }

        return ReceiverOptions.<String,TransportMessage>create(consumerProps)
                .pollTimeout(Duration.of(this.kafkaConfig.getSpecificConsumer().getPollTimeout(), ChronoUnit.MILLIS))
                .subscription(Collections.singleton(this.kafkaConfig.getSpecificConsumer().getTopic()));
    }
}
