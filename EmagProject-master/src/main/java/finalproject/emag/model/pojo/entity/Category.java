package finalproject.emag.model.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "categories")
public final class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
}