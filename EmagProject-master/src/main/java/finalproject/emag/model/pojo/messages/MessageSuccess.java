package finalproject.emag.model.pojo.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageSuccess {

    private String message;
    private LocalDateTime time;
}