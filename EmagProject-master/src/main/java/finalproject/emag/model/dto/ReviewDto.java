package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {

    private Long userId;
    private Long productId;
    private String title;
    private String comment;
    private Integer grade;
}