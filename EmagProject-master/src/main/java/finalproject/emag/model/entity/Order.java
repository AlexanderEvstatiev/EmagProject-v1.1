package finalproject.emag.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public final class Order {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @NotNull
    private User user;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDate date;

    @Transient
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
                Objects.equals(getUser(), order.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser());
    }
}