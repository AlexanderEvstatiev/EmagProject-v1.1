package finalproject.emag.model.dto;

import lombok.Data;

@Data
public class RemovePromotionDto {

    private long productId;
    private double price;

    public RemovePromotionDto(long productId) {
        this.productId = productId;
    }
}