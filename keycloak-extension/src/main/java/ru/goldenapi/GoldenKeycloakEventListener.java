package ru.goldenapi;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class GoldenKeycloakEventListener implements EventListenerProvider {
    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {

    }

    @Override
    public void close() {

    }
}
