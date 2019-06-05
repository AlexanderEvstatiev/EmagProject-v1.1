package finalproject.emag.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class CartProductDto {

    private long id;
    private String name;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProductDto that = (CartProductDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}