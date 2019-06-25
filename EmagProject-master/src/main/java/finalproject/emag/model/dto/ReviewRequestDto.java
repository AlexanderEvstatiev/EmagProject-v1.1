package finalproject.emag.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
public class ReviewRequestDto {

    private String title;
    private String comment;
    private Integer grade;
}