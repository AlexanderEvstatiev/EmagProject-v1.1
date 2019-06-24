package finalproject.emag.model.dto;

import lombok.Data;

@Data
public class RemoveDiscountDto {

    private long productId;
    private double price;

    public RemoveDiscountDto(long productId) {
        this.productId = productId;
    }
}