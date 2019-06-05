package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartViewProductDto {

    private long id;
    private String name;
    private int quantity;
    private double price;
}