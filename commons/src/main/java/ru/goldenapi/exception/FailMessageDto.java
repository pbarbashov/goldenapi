package ru.goldenapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.goldenapi.dto.TransportMessage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailMessageDto implements TransportMessage {

    private String message;
    private Exception exception;
}
