package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ViewUserDto {

    private long id;
    private String email;
    private String name;
    private String username;
    private String phoneNumber;
    private LocalDate birthDate;
    private boolean subscribed;
    private String imageUrl;
}