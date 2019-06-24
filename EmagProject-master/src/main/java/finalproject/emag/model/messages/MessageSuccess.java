package finalproject.emag.model.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public final class MessageSuccess {

    private String message;
    private LocalDateTime time;
}