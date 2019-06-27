package finalproject.emag.model.repository;

import finalproject.emag.model.entity.Product;
import finalproject.emag.model.entity.Review;
import finalproject.emag.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    @Query(value = "SELECT * FROM reviews WHERE product_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<Review> findReviewByProductIdAndUserId(long productId, long userId);

    List<Review> findById_Product(Product product);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reviews WHERE product_id = ?1 AND user_id = ?2", nativeQuery = true)
    void deleteReviewByProductIdAndUserId(long productId, long userId);

    @Query(value = "SELECT ROUND(AVG(grade)) FROM reviews WHERE product_id = ?1", nativeQuery = true)
    int getReviewsAvgGradeForProduct(long productId);

    @Query(value = "SELECT COUNT(*) FROM reviews WHERE product_id = ?1", nativeQuery = true)
    int getReviewsCountForProduct(long productId);
}
