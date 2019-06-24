package finalproject.emag.model.dto;

import lombok.Data;

import java.util.HashSet;

@Data
public class AddProductDto {

    private long categoryId;
    private String name;
    private double price;
    private int quantity;
    private String image;
    private HashSet<StatInsertDto> stats = new HashSet<>();
}