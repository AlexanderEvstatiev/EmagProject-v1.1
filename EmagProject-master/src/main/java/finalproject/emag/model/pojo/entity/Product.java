package finalproject.emag.model.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private  Long id;

    @OneToOne
    private Category category;

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    private  String imageUrl;
}