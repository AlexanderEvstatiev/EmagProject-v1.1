package finalproject.emag.model.repository;

import finalproject.emag.model.entity.Stat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface StatRepository extends PagingAndSortingRepository<Stat, Long> {

    List<Stat> findStatByProductId(long productId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM stats WHERE product_id=?1", nativeQuery = true)
    void deleteAllByProductId(long productId);
}
