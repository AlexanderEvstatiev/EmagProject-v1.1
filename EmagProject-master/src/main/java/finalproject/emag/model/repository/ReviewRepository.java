package finalproject.emag.model.repository;

import finalproject.emag.model.pojo.entity.Product;
import finalproject.emag.model.pojo.entity.Review;
import finalproject.emag.model.pojo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    Optional<Review> findByIdProductAndIdUser(Product productId, User userId);

    List<Review> findById_Product(Product product);

    @Query(value = "SELECT ROUND(AVG(grade)) FROM reviews WHERE product_id = ?1", nativeQuery = true)
    int getReviewsAvgGradeForProduct(Long productId);

    @Query(value = "SELECT COUNT(*) FROM reviews WHERE product_id = ?1", nativeQuery = true)
    int getReviewsCountForProduct(Long productId);
}
