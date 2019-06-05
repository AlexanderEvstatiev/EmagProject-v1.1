package finalproject.emag.model.dto;

import lombok.Data;


@Data
public class GlobalViewProductDto {

    private Long id;
    private String name;
    private double price;
    private int quantity;
    private int reviewsCount;
    private int reviewsGrade;
}