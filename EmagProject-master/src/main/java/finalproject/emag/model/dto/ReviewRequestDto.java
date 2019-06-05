package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDto {

    private String title;
    private String comment;
    private Integer grade;
}