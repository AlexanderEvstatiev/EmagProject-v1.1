package finalproject.emag.model.entity;

import finalproject.emag.model.entity.keys.OrderedProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "ordered_products")
public final class OrderedProduct {

    @EmbeddedId
    private OrderedProductId id;

    @Column(nullable = false)
    private int quantity;
}