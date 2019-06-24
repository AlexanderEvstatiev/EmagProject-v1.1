package finalproject.emag.model.dto;

import lombok.Data;


@Data
public class DiscountProductDto {

    private String startDate;
    private String endDate;
    private double newPrice;
}