package finalproject.emag.model.repository;

import finalproject.emag.model.entity.Stat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StatRepository extends PagingAndSortingRepository<Stat, Long> {

    @Query(value = "SELECT * FROM stats WHERE product_id = ?1", nativeQuery = true)
    List<Stat> findStatByProductId(Long productId);
}
