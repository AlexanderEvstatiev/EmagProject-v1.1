package finalproject.emag.model.entity.keys;

import finalproject.emag.model.entity.Product;
import finalproject.emag.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReviewId implements Serializable {

    @OneToOne
    @NotNull
    private User user;

    @OneToOne
    @NotNull
    private Product product;
}