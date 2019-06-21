package finalproject.emag.model.dto;

import finalproject.emag.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductViewDto {

    private Long id;
    private Category category;
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;
    private HashSet<StatInsertDto> stats;
    private HashSet<ReviewViewDto> reviews;

    public void addToStats(StatInsertDto stat) { stats.add(stat); }

    public void addToReviews(ReviewViewDto review) {
        reviews.add(review);
    }
}