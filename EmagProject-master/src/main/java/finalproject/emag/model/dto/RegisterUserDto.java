package finalproject.emag.model.dto;

import lombok.Data;

@Data

public class RegisterUserDto {

    private String email;
    private String name;
    private String firstPassword;
    private String secondPassword;
    private boolean subscribed;
}