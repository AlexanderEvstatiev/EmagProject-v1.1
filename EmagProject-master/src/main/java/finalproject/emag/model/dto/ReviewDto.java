package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReviewDto {

    private long userId;
    private long productId;
    private String title;
    private String comment;
    private int grade;
}