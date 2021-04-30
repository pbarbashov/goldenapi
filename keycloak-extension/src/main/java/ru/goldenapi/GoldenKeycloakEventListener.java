package ru.goldenapi;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import ru.goldenapi.dto.TransportMessage;
import ru.goldenapi.dto.keycloak.events.UserChanged;

import java.util.UUID;

@Slf4j
public class GoldenKeycloakEventListener implements EventListenerProvider {
    private final KafkaSender<String, TransportMessage> kafkaSender;
    private final String topic;
    private final KeycloakSession keycloakSession;

    public GoldenKeycloakEventListener(KafkaSender<String, TransportMessage> kafkaSender, String topic, KeycloakSession keycloakSession) {
        this.kafkaSender = kafkaSender;
        this.topic = topic;
        this.keycloakSession = keycloakSession;
    }

    @Override
    public void onEvent(Event event) {
        kafkaSender.send(eventMapper(event))
                .subscribe();
    }

    private Mono<SenderRecord<String, TransportMessage, UUID>> eventMapper(Event event) {
        UUID messageId = UUID.randomUUID();
        RealmModel realm = keycloakSession.realms().getRealm(event.getRealmId());
        UserModel user = keycloakSession.users().getUserById(event.getUserId(), realm);
        UserChanged userChanged = new UserChanged();
        userChanged.userId(event.getUserId())
                .emailVerified(user.isEmailVerified())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .phoneNumber("");
        var producerRecord = new ProducerRecord<>(topic,messageId.toString(), (TransportMessage) userChanged);
        return Mono.just(SenderRecord.create(producerRecord,messageId));
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {

    }

    @Override
    public void close() {
    }
}
