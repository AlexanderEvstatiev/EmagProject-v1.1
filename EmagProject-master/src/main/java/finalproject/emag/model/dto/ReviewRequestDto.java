package finalproject.emag.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequestDto {

    private String title;
    private String comment;
    private Integer grade;
}