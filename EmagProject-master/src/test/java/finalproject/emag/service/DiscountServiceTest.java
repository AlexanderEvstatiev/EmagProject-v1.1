package finalproject.emag.service;

import finalproject.emag.model.dto.DiscountProductDto;
import finalproject.emag.model.entity.Discount;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.repository.DiscountRepository;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.util.DiscountNotify;
import finalproject.emag.util.exception.BaseException;
import finalproject.emag.util.exception.ProductNotFoundException;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountNotify discountNotify;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addDiscount() {
        Product product = new Product();
        DiscountProductDto discount = new DiscountProductDto();
        discount.setEndDate("10/11/1999");
        discount.setStartDate("9/9/1999");
        discount.setNewPrice(500);
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(product);
        discountService.addDiscount(discount, 1L);
        assertEquals(500, product.getPrice(), 0.0);
    }

    @Test(expected = ProductNotFoundException.class)
    public void addDiscountNoSuchProduct() {
        DiscountProductDto discount = new DiscountProductDto();
        discount.setEndDate("10/11/1999");
        discount.setStartDate("9/9/1999");
        discount.setNewPrice(500);
        Mockito.when(productService.checkIfProductExists(1L)).thenThrow(ProductNotFoundException.class);
        discountService.addDiscount(discount, 1L);
    }

    @Test
    public void removeDiscount() {
        Product product = new Product();
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(product);
        Discount discount = new Discount();
        discount.setOldPrice(600);
        Mockito.when(discountRepository.findByProductId(1L)).thenReturn(Optional.of(discount));
        discountService.removeDiscount(1L);
        assertEquals(600, product.getPrice(), 0.0);
    }

    @Test(expected = BaseException.class)
    public void removeDiscountNotExist() {
        Mockito.when(discountRepository.findByProductId(1L)).thenReturn(Optional.empty());
        discountService.removeDiscount(1L);
    }
}
