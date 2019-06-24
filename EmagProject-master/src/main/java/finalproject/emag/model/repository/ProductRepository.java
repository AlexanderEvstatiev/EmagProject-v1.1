package finalproject.emag.model.repository;

import finalproject.emag.model.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
        QuerydslPredicateExecutor<Product> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET quantity = quantity - ?1 WHERE id = ?2", nativeQuery = true)
    void changeProductQuantityAfterOrder(int quantity, long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM products WHERE id=?1", nativeQuery = true)
    void deleteProductById(Long id);

    List<Product> findAll();

    List<Product> findAllByCategoryId(long categoryId);

    List<Product> findByNameIgnoreCaseContaining(String name);
}
