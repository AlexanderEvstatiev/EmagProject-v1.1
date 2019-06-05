package finalproject.emag.model.pojo.composite.keys;

import finalproject.emag.model.pojo.entity.Order;
import finalproject.emag.model.pojo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProductId implements Serializable {

    @OneToOne
    private Order order;

    @OneToOne
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedProductId that = (OrderedProductId) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}