package finalproject.emag.model.repository;

import finalproject.emag.model.entity.Discount;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends PagingAndSortingRepository<Discount, Long> {

    Optional<Discount> findByProductId(long id);

    Discount findById();
}
