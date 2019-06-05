package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifyUserDto {

    private String email;
    private String name;
}