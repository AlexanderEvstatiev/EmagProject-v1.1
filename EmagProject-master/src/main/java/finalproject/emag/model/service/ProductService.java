package finalproject.emag.model.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import finalproject.emag.model.dto.*;
import finalproject.emag.model.pojo.entity.*;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.model.repository.ReviewRepository;
import finalproject.emag.model.repository.StatRepository;
import finalproject.emag.util.exception.BaseException;
import finalproject.emag.util.exception.ProductNotFoundException;
import finalproject.emag.util.exception.ProductOutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private StatRepository statRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    private ArrayList<GlobalViewProductDto> products(List<Product> productList) {
        ArrayList<GlobalViewProductDto> products = new ArrayList<>();
        for (Product product : productList) {
            GlobalViewProductDto p = new GlobalViewProductDto();
            p.setId(product.getId());
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            p.setQuantity(product.getQuantity());
            int reviewsCount = reviewService.getReviewsCountForProduct(product.getId());
            p.setReviewsCount(reviewsCount);
            if (reviewsCount > 0) {
                p.setReviewsGrade(reviewService.getReviewsAvgGradeForProduct(product.getId()));
            }
            products.add(p);
        }
        return  products;
    }

    public ArrayList<GlobalViewProductDto> getAllProducts() {
        return products(productRepository.findAll());
    }

    public ArrayList<GlobalViewProductDto> getAllProductsByCategory(Long categoryId) {
        checkCategoryId(categoryId);
        return products(productRepository.findAllByCategoryId(categoryId));
    }

    //TODO CHECK
    public ArrayList<GlobalViewProductDto> getAllProductsBySubcategoryFiltered
            (long id, String order, Double min, Double max){
        checkCategoryId(id);
        BooleanExpression booleanExpression = QProduct.product.price.between(min, max).and(QProduct.product.category.id.eq(id));
        OrderSpecifier<Double> orderSpecifier;
        if (order.equals("DESC")) {
            orderSpecifier = QProduct.product.price.desc();
        } else {
            orderSpecifier = QProduct.product.price.asc();
        }
        Iterable<Product> iterable = productRepository.findAll(booleanExpression, orderSpecifier);
        List<Product> employees = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        return products(employees);
    }

    private void checkCategoryId(Long categoryId) {
        List<Product> products  = productRepository.findAllByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new BaseException("No such category!");
        }
    }

    public ArrayList<GlobalViewProductDto> getAllProductsFiltered(String order, Double min, Double max) {
        BooleanExpression booleanExpression = QProduct.product.price.between(min, max);
        OrderSpecifier<Double> orderSpecifier;
        if (order.equals("DESC")) {
            orderSpecifier = QProduct.product.price.desc();
        } else {
            orderSpecifier = QProduct.product.price.asc();
        }
        Iterable<Product> iterable = productRepository.findAll(booleanExpression, orderSpecifier);
        List<Product> employees = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        return products(employees);
    }

//    private Double getMaxPriceOfProduct() {
//        return productRepository.findMaxPrice();
//    }
//
//    private double getMaxPriceOfProductForSubcategory(long subcatId) {
//        return productRepository.findMaxPriceByCategory(subcatId);
//    }
//
//    private double getMinPriceOfProduct() {
//        return productRepository.findMinPrice();
//    }
//
//    private double getMinPriceOfProductForSubcategory(long subcatId) {
//        return productRepository.findMinPriceByCategory(subcatId);
//    }

    public ArrayList<GlobalViewProductDto> searchProducts(String name) {
        return products(productRepository.findByNameIgnoreCaseContaining(name));
    }

    public ProductViewDto getProductById(long productId) {
        checkIfProductExists(productId);
        Product savedProduct = productRepository.findById(productId).get();
        ProductViewDto product = ProductViewDto.builder().id(savedProduct.getId())
                .category(savedProduct.getCategory()).name(savedProduct.getName())
                .price(savedProduct.getPrice()).imageUrl(savedProduct.getImageUrl())
                .quantity(savedProduct.getQuantity()).reviews(new HashSet<>()).stats(new HashSet<>()).build();
        List<Stat> stats = statRepository.findStatByProductId(productId);
        for (Stat stat: stats) {
            StatInsertDto insert = new StatInsertDto(stat.getName(), stat.getUnit(), stat.getValue());
            product.addToStats(insert);
        }
        addReviewsToProduct(product, savedProduct);
        return product;
    }

    private void addReviewsToProduct(ProductViewDto product, Product savedProduct) {
        List<Review> reviews = reviewRepository.findById_Product(savedProduct);
        for (Review review : reviews) {
            ReviewViewDto view = new ReviewViewDto(review.getId().getUser().getId(),
                    review.getTitle(), review.getComment(), review.getGrade());
            product.addToReviews(view);
        }
    }

    void checkIfProductExists(Long productId) {
        productRepository.findById(productId).orElseThrow(() ->  new ProductNotFoundException("No such product."));
    }

    void checkProductQuantity(long id, int products) {
        Product product = productRepository.findById(id).get();
        if (product.getQuantity() < products)
            throw new ProductOutOfStockException("This product does not have the wanted quantity.");
    }

    public void changeQuantity(long id, int quantity) {
        checkIfProductExists(id);
        productRepository.changeProductQuantity(quantity, id);
    }

    public void deleteProduct(long id) {
        checkIfProductExists(id);
        productRepository.deleteProductById(id);
    }

    @Transactional
    public void insertProductInDB(AddProductDto product) {
        Product savedProduct = addProduct(product);
        addStats(product, savedProduct);
    }

    private Product addProduct(AddProductDto input) {
        Category category = Category.builder().id(input.getCategoryId()).build();
        return productRepository.save(Product.builder().
                category(category).name(input.getName()).price(input.getPrice())
                .quantity(input.getQuantity()).imageUrl(input.getImage()).build());
    }

    private void addStats(AddProductDto product, Product savedProduct) {
        HashSet<StatInsertDto> stats = product.getStats();
        for (StatInsertDto stat : stats) {
            Stat saveStat = Stat.builder()
                    .product(savedProduct).category(savedProduct.getCategory())
                    .name(stat.getName()).unit(stat.getUnit()).value(stat.getValue()).build();
            statRepository.save(saveStat);
        }

    }
}
