package finalproject.emag.model.pojo.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMsg {

    private String msg;
    private int status;
    private LocalDateTime time;
}