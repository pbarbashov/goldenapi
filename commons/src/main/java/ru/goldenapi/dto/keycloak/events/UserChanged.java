package ru.goldenapi.dto.keycloak.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.goldenapi.dto.TransportMessage;

/**
 * Event that is emitted when user attributes changed
 */

@Data
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class UserChanged implements TransportMessage {
    private String eventType;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean emailVerified;
    private boolean enabled;
}
