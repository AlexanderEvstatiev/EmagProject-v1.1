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

//    @Query(value = "SELECT MAX(price) FROM products", nativeQuery = true)
//    Double findMaxPrice();
//
//    @Query(value = "SELECT MIN(price) FROM products", nativeQuery = true)
//    Double findMinPrice();
//
//    @Query(value = "SELECT MAX(price) FROM products WHERE category_id = ?1", nativeQuery = true)
//    Double findMaxPriceByCategory(Long categoryId);
//
//    @Query(value = "SELECT MIN(price) FROM products WHERE category_id = ?1", nativeQuery = true)
//    Double findMinPriceByCategory(Long categoryId);

    @Query(value = "DELETE FROM products WHERE id=?1", nativeQuery = true)
    void deleteProductById(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET quantity = ?1 WHERE id = ?2", nativeQuery = true)
    void changeProductQuantity(Integer quantity, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET quantity = quantity - ?1 WHERE id = ?2", nativeQuery = true)
    void changeProductQuantityAfterOrder(Integer quantity, Long id);

    List<Product> findAll();

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findByNameIgnoreCaseContaining(String name);
}
