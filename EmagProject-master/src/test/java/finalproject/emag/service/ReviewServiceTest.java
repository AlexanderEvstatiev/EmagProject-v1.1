package finalproject.emag.service;

import finalproject.emag.model.dto.ReviewRequestDto;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.entity.Review;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.repository.ReviewRepository;
import finalproject.emag.util.exception.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductService productService;

    private static User user = User.builder().id(1L).build();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addReview() {
        Review review = new Review();
        ReviewRequestDto request = ReviewRequestDto.builder().title("title").comment("comment").grade(5).build();
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(new Product());
        Mockito.when(reviewRepository.findReviewByProductIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        Mockito.when(reviewRepository.save(review)).thenReturn(review);
        reviewService.addReview(request, 1L, user);
    }

    @Test(expected = ProductNotFoundException.class)
    public void addReviewProductNotExist() {
        ReviewRequestDto request = ReviewRequestDto.builder().title("title").comment("comment").grade(5).build();
        Mockito.when(productService.checkIfProductExists(1L)).thenThrow(ProductNotFoundException.class);
        reviewService.addReview(request, 1L, user);
    }

    @Test(expected = MissingValuableFieldsException.class)
    public void addReviewMissingInputFields() {
        ReviewRequestDto request = ReviewRequestDto.builder().comment("comment").grade(5).build();
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(new Product());
        reviewService.addReview(request, 1L, user);
    }

    @Test(expected = BaseException.class)
    public void addReviewInvalidGrade() {
        ReviewRequestDto request = ReviewRequestDto.builder().title("title").comment("comment").grade(0).build();
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(new Product());
        reviewService.addReview(request, 1L, user);
    }

    @Test(expected = ReviewExistsException.class)
    public void addReviewAlreadyExist() {
        ReviewRequestDto request = ReviewRequestDto.builder().title("title").comment("comment").grade(5).build();
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(new Product());
        Mockito.when(reviewRepository.findReviewByProductIdAndUserId(1L, 1L)).thenReturn(Optional.of(new Review()));
        reviewService.addReview(request, 1L, user);
    }

    @Test
    public void deleteReview() {
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(new Product());
        Mockito.when(reviewRepository.findReviewByProductIdAndUserId(1L, 1L)).thenReturn(Optional.of(new Review()));
        reviewService.deleteReview(1L, user);
    }

    @Test(expected = ProductNotFoundException.class)
    public void deleteReviewProductNotExist() {
        Mockito.when(productService.checkIfProductExists(1L)).thenThrow(ProductNotFoundException.class);
        reviewService.deleteReview(1L, user);
    }

    @Test(expected = ReviewMissingException.class)
    public void deleteReviewNotExist() {
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(new Product());
        Mockito.when(reviewRepository.findReviewByProductIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        reviewService.deleteReview(1L ,user);
    }

    @Test
    public void editReview() {
        Review review = new Review();
        ReviewRequestDto request = ReviewRequestDto.builder().title("title").comment("comment").grade(5).build();
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(new Product());
        Mockito.when(reviewRepository.findReviewByProductIdAndUserId(1L, 1L)).thenReturn(Optional.of(new Review()));
        Mockito.when(reviewRepository.save(review)).thenReturn(review);
        reviewService.editReview(request, 1L, user);
    }

    @Test
    public void getReviewCountForProduct() {
        Mockito.when(reviewRepository.getReviewsCountForProduct(1L)).thenReturn(1);
        Assert.assertEquals(1, reviewService.getReviewsCountForProduct(1L));
    }

    @Test
    public void getReviewAvgGradeForProduct() {
        Mockito.when(reviewRepository.getReviewsAvgGradeForProduct(1L)).thenReturn(1);
        Assert.assertEquals(1, reviewService.getReviewsAvgGradeForProduct(1L));
    }
}
