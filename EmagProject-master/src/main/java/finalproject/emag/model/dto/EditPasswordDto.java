package finalproject.emag.model.dto;

import lombok.Data;

@Data
public class EditPasswordDto {

    private String currentPassword;
    private String firstPassword;
    private String secondPassword;
}