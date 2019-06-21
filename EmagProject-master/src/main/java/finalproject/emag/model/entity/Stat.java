package finalproject.emag.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stats")
public final class Stat {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @NotNull
    private Product product;

    @OneToOne
    @NotNull
    private Category category;

    @Column(nullable = false)
    private String name;

    private String unit;

    @Column(nullable = false)
    private String value;
}