package finalproject.emag.model.entity.keys;

import finalproject.emag.model.entity.Order;
import finalproject.emag.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProductId implements Serializable {

    @OneToOne
    private Order order;

    @OneToOne
    private Product product;
}