package finalproject.emag.model.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "discounts")
public final class Discount {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @NotNull
    private Product product;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Double oldPrice;

    @Column(nullable = false)
    private Double newPrice;
}