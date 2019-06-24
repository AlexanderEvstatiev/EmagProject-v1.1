package finalproject.emag.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

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
}