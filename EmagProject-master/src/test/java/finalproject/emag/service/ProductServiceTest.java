package finalproject.emag.service;

import finalproject.emag.model.dto.GlobalViewProductDto;
import finalproject.emag.model.entity.Category;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.repository.ProductRepository;
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
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewService reviewService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllProductsTestSize() {
        Category category = new Category(1, "CPU");
        Product product = new Product();
        product.setId(1L);
        product.setCategory(category);
        product.setPrice(500);
        product.setQuantity(10);
        product.setName("Intel i5");
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProducts();
        Assert.assertEquals(1, products2.size());
    }

    @Test
    public void findAllProductsTestContent() {
        Category category = new Category(1, "CPU");
        Product product = new Product();
        product.setId(1L);
        product.setCategory(category);
        product.setPrice(500);
        product.setQuantity(10);
        product.setName("Intel i5");
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
        Category category = new Category(1, "CPU");
        Product product = new Product();
        product.setId(1L);
        product.setCategory(category);
        product.setPrice(500);
        product.setQuantity(10);
        product.setName("Intel i5");
        List<Product> products = new ArrayList<>();
        products.add(product);
        Mockito.when(productRepository.findAllByCategoryId(1)).thenReturn(products);
        Mockito.when(reviewService.getReviewsCountForProduct(1L)).thenReturn(0);
        ArrayList<GlobalViewProductDto> products2 = productService.getAllProductsByCategory(1);
        Assert.assertEquals(1, products2.size());
    }

    @Test(expected = BaseException.class)
    public void findAllByCategoryNotExist() {
        Mockito.when(productRepository.findAllByCategoryId(1)).thenReturn(new ArrayList<>());
        ArrayList<GlobalViewProductDto> products = productService.getAllProductsByCategory(1);
    }

}
