package finalproject.emag.service;

import finalproject.emag.model.dto.ReviewDto;
import finalproject.emag.model.dto.ReviewRequestDto;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.entity.Review;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.entity.keys.ReviewId;
import finalproject.emag.model.repository.ReviewRepository;
import finalproject.emag.util.exception.BaseException;
import finalproject.emag.util.exception.MissingValuableFieldsException;
import finalproject.emag.util.exception.ReviewExistsException;
import finalproject.emag.util.exception.ReviewMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private ProductService productService;

    public void addReview(ReviewRequestDto request, long productId, User user) {
        ReviewDto review = getReview(request, productId, user);
        checkForReviewPresent(review, user);
        this.reviewRepo.save(constructReview(review));
    }

    public void deleteReview(long productId, User user) {
        productService.checkIfProductExists(productId);
        checkForReviewMissing(productId, user);
        this.reviewRepo.deleteReviewByProductIdAndUserId(productId, user.getId());
    }

    public void editReview(ReviewRequestDto request, long productId, User user) {
        ReviewDto review = getReview(request, productId, user);
        checkForReviewMissing(productId, user);
        this.reviewRepo.save(constructReview(review));
    }

    int getReviewsCountForProduct(long productId){
        return reviewRepo.getReviewsCountForProduct(productId);
    }

    int getReviewsAvgGradeForProduct(long productId){
        return reviewRepo.getReviewsAvgGradeForProduct(productId);
    }

    private ReviewDto getReview(ReviewRequestDto review, long productId, User user) {
        productService.checkIfProductExists(productId);
        checkRequestReview(review);
        return new ReviewDto(user.getId(), productId, review.getTitle(), review.getComment(), review.getGrade());
    }

    private void checkRequestReview(ReviewRequestDto review) {
        if (review.getComment() == null || review.getTitle() == null || review.getGrade() == null) {
            throw new MissingValuableFieldsException();
        }
        if (review.getGrade() < 1 || review.getGrade() > 5) {
            throw new BaseException("Invalid review grade!");
        }
    }

    private Review constructReview(ReviewDto review) {
        return new Review(constructReviewId(review), review.getTitle(), review.getComment(), review.getGrade());
    }

    private ReviewId constructReviewId(ReviewDto review) {
        User user = User.builder().id(review.getUserId()).build();
        Product product = Product.builder().id(review.getProductId()).build();
        return new ReviewId(user, product);
    }

    private void checkForReviewMissing (long reviewId, User user) {
        reviewRepo.findReviewByProductIdAndUserId(reviewId, user.getId())
                .orElseThrow(ReviewMissingException::new);
    }

    private void checkForReviewPresent (ReviewDto review, User user) {
        Optional<Review> savedReview = reviewRepo.
                findReviewByProductIdAndUserId(review.getProductId(), user.getId());
        if (savedReview.isPresent()) {
            throw new ReviewExistsException();
        }
    }
}