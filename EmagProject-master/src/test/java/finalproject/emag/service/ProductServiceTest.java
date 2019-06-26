package finalproject.emag.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import finalproject.emag.model.dto.AddProductDto;
import finalproject.emag.model.dto.GlobalViewProductDto;
import finalproject.emag.model.dto.ProductViewDto;
import finalproject.emag.model.dto.StatInsertDto;
import finalproject.emag.model.entity.*;
import finalproject.emag.model.entity.keys.ReviewId;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.model.repository.ReviewRepository;
import finalproject.emag.model.repository.StatRepository;
import finalproject.emag.util.exception.BaseException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewService reviewService;

    @Mock
    private StatRepository statRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private QuerydslPredicateExecutor executor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllProductsTestSize() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProducts();
        Assert.assertEquals(1, products2.size());
    }

    @Test
    public void findAllProductsTestContent() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProducts();
        Assert.assertEquals("Intel i5", products2.get(0).getName());
    }

    @Test
    public void findAllProductsEmpty() {
        Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<GlobalViewProductDto> products = productService.getAllProducts();
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void findAllByCategory() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findAllByCategoryId(1)).thenReturn(products);
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(1);
        Mockito.when(reviewService.getReviewsAvgGradeForProduct(1L)).thenReturn(3);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProductsByCategory(1);
        Assert.assertEquals(1, products2.size());
    }

    @Test(expected = BaseException.class)
    public void findAllByCategoryNotExist() {
        Mockito.when(productRepository.findAllByCategoryId(1)).thenReturn(new ArrayList<>());
        ArrayList<GlobalViewProductDto> products = productService.getAllProductsByCategory(1);
    }

    @Test
    public void findAllFilteredASC() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        BooleanExpression booleanExpression = QProduct.product.price.between(0, 600);
        OrderSpecifier<Double> orderSpecifier = QProduct.product.price.asc();
        Mockito.when(executor.findAll(booleanExpression, orderSpecifier)).thenReturn((Iterable<Product>)() -> products.iterator());
        Mockito.when(productRepository.findAll(booleanExpression, orderSpecifier)).thenReturn(() -> products.iterator());
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProductsFiltered("ASC", 0, 600);
        Assert.assertEquals(1, products2.size());
    }

    @Test
    public void findAllFilteredDESC() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        BooleanExpression booleanExpression = QProduct.product.price.between(0, 600);
        OrderSpecifier<Double> orderSpecifier = QProduct.product.price.desc();
        Mockito.when(executor.findAll(booleanExpression, orderSpecifier)).thenReturn((Iterable<Product>)() -> products.iterator());
        Mockito.when(productRepository.findAll(booleanExpression, orderSpecifier)).thenReturn(() -> products.iterator());
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProductsFiltered("DESC", 0, 600);
        Assert.assertEquals(1, products2.size());
    }

    @Test
    public void findAllFilteredByCategoryASC() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        BooleanExpression booleanExpression = QProduct.product.price.between(0, 600)
                .and(QProduct.product.category.id.eq(1L));
        OrderSpecifier<Double> orderSpecifier = QProduct.product.price.asc();
        Mockito.when(productRepository.findAllByCategoryId(1)).thenReturn(products);
        Mockito.when(executor.findAll(booleanExpression, orderSpecifier)).thenReturn((Iterable<Product>)() -> products.iterator());
        Mockito.when(productRepository.findAll(booleanExpression, orderSpecifier)).thenReturn(() -> products.iterator());
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProductsByCategoryFiltered(1, "ASC", 0, 600);
        Assert.assertEquals(1, products2.size());
    }

    @Test
    public void findAllFilteredByCategoryDESC() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        BooleanExpression booleanExpression = QProduct.product.price.between(0, 600)
                .and(QProduct.product.category.id.eq(1L));
        OrderSpecifier<Double> orderSpecifier = QProduct.product.price.desc();
        Mockito.when(productRepository.findAllByCategoryId(1)).thenReturn(products);
        Mockito.when(executor.findAll(booleanExpression, orderSpecifier)).thenReturn((Iterable<Product>)() -> products.iterator());
        Mockito.when(productRepository.findAll(booleanExpression, orderSpecifier)).thenReturn(() -> products.iterator());
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProductsByCategoryFiltered(1, "DESC", 0, 600);
        Assert.assertEquals(1, products2.size());
    }

    @Test(expected = BaseException.class)
    public void findAllFilteredByCategoryNotExist() {
        Mockito.when(productRepository.findAllByCategoryId(1)).thenReturn(new ArrayList<>());
        ArrayList<GlobalViewProductDto> products = productService.getAllProductsByCategoryFiltered(1, "ASC", 0, 600);
    }

    @Test
    public void findByName() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findByNameIgnoreCaseContaining("Intel")).thenReturn(products);
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.searchProducts("Intel");
        Assert.assertEquals("Intel i5", products2.get(0).getName());
    }

    @Test
    public void findProductById() {
        Product product = createProduct();
        User user = new User();
        user.setId(1L);
        Review review = new Review(new ReviewId(user, product), "review", "smth", 3);
        Stat stat = new Stat(1L, product, product.getCategory(), "stat", "smth", "value");
        ArrayList<Stat> stats = new ArrayList<>();
        stats.add(stat);
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(review);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(statRepository.findStatByProductId(1L)).thenReturn(stats);
        Mockito.when(reviewRepository.findById_Product(product)).thenReturn(reviews);
        ProductViewDto productDto = productService.getProductById(1L);
        Assert.assertEquals(product.getName(), productDto.getName());
    }

    @Test(expected = BaseException.class)
    public void findProductByIdNotExist() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        productService.getProductById(1L);
    }

    @Test
    public void changeQuantityProduct() {
        Product product = createProduct();
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        productService.changeQuantity(1L, 20);
        Assert.assertEquals(20, product.getQuantity());
    }

    @Test(expected = BaseException.class)
    public void changeQuantityProductNotExist() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        productService.changeQuantity(1L, 20);
    }

    @Test
    public void deleteProduct() {
        Product product = createProduct();
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.deleteProduct(1L);
    }

    @Test(expected = BaseException.class)
    public void deleteProductNotExist() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        productService.deleteProduct(1L);
    }

    @Test
    public void addProduct() {
        Product product = createProduct();
        product.setImageUrl("img");
        AddProductDto addProduct = new AddProductDto();
        addProduct.setCategoryId(1L);
        addProduct.setName("Intel");
        addProduct.setPrice(500);
        addProduct.setQuantity(10);
        addProduct.setImage("img");
        StatInsertDto stat = new StatInsertDto("Cores", null, "8");
        HashSet<StatInsertDto> stats = new HashSet<>();
        stats.add(stat);
        addProduct.setStats(stats);
        Stat saveStat = new Stat();
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(statRepository.save(saveStat)).thenReturn(saveStat);
        Mockito.when(productService.addProduct(addProduct)).thenReturn(product);
        productService.insertProductInDB(addProduct);
    }

    @Test
    public void testProductQuantity() {
        Product product = createProduct();
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.checkProductQuantity(1L, 5);
        Assert.assertTrue(product.getQuantity() > 5);
    }

    @Test(expected = BaseException.class)
    public void testProductQuantityNotEnough() {
        Product product = createProduct();
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.checkProductQuantity(1L, 15);
    }

    private Product createProduct() {
        Category category = new Category(1, "CPU");
        Product product = new Product();
        product.setId(1L);
        product.setCategory(category);
        product.setPrice(500);
        product.setQuantity(10);
        product.setName("Intel i5");
        return product;
    }
}
