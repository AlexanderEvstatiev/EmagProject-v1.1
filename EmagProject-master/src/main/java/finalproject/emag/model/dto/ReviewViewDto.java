package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewViewDto {

    private Long userId;
    private String title;
    private String comment;
    private Integer grade;
}