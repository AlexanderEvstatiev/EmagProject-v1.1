package finalproject.emag.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditPersonalInfoDto {

    private String name;
    private String username;
    private String phoneNumber;
    private String birthDate;
}
