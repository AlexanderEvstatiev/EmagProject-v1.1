package finalproject.emag.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
public class AddProductDto {

    private long categoryId;
    private String name;
    private double price;
    private int quantity;
    private String image;
    private HashSet<StatInsertDto> stats = new HashSet<>();
}