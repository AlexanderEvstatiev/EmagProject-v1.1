package finalproject.emag.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartProductDto {

    private long id;
    private String name;
    private double price;
}