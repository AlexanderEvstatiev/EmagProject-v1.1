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
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(getUser(), reviewId.getUser()) &&
                Objects.equals(getProduct(), reviewId.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getProduct());
    }
}