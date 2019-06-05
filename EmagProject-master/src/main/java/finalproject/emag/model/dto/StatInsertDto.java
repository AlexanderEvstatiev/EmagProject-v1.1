package finalproject.emag.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatInsertDto {

    private String name;
    private String unit;
    private String value;
}