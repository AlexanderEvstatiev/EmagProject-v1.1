package finalproject.emag.model.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public final class ErrorMsg {

    private String msg;
    private int status;
    private LocalDateTime time;
}