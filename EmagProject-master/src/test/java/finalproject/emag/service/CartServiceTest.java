package finalproject.emag.service;

import finalproject.emag.model.dto.CartProductDto;
import finalproject.emag.model.dto.CartViewProductDto;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.util.exception.BaseException;
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
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void viewCart() {
        CartProductDto cartProductDto = CartProductDto.builder().id(1L).name("Intel i5").price(500).build();
        HashMap<CartProductDto, Integer> cart = new HashMap<>();
        cart.put(cartProductDto, 2);
        ArrayList<CartViewProductDto> viewCart = cartService.viewCart(cart);
        Assert.assertEquals(1, viewCart.size());
    }

    @Test
    public void viewCartEmpty() {
        ArrayList<CartViewProductDto> viewCart = cartService.viewCart(new HashMap<>());
        Assert.assertEquals(0, viewCart.size());
    }

    @Test
    public void addProductToCartAlreadyInside() {
        CartProductDto cartProductDto = CartProductDto.builder().id(1L).name("Intel i5").price(500).build();
        HashMap<CartProductDto, Integer> cart = new HashMap<>();
        cart.put(cartProductDto, 2);
        Product product = new Product();
        product.setPrice(500);
        product.setId(1L);
        product.setName("Intel i5");
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(product);
        cartService.addProductToCart(1L, cart);
        Assert.assertEquals(3, cart.get(cartProductDto).intValue());
    }

    @Test
    public void addProductToCartFirstTime() {
        HashMap<CartProductDto, Integer> cart = new HashMap<>();
        Product product = new Product();
        product.setPrice(500);
        product.setId(1L);
        product.setName("Intel i5");
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(product);
        cartService.addProductToCart(1L, cart);
        Assert.assertTrue(cart.containsValue(1));
    }

    @Test
    public void removeProductFromCartStillInside() {
        CartProductDto cartProductDto = CartProductDto.builder().id(1L).name("Intel i5").price(500).build();
        HashMap<CartProductDto, Integer> cart = new HashMap<>();
        cart.put(cartProductDto, 2);
        Product product = new Product();
        product.setPrice(500);
        product.setId(1L);
        product.setName("Intel i5");
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(product);
        cartService.removeProductFromCart(1L, cart);
        Assert.assertEquals(1, cart.get(cartProductDto).intValue());
    }

    @Test
    public void removeProductFromCartNotInside() {
        CartProductDto cartProductDto = CartProductDto.builder().id(1L).name("Intel i5").price(500).build();
        HashMap<CartProductDto, Integer> cart = new HashMap<>();
        cart.put(cartProductDto, 1);
        Product product = new Product();
        product.setPrice(500);
        product.setId(1L);
        product.setName("Intel i5");
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(product);
        cartService.removeProductFromCart(1L, cart);
        Assert.assertFalse(cart.containsKey(cartProductDto));
    }

    @Test(expected = BaseException.class)
    public void removeProductFromCartNotExist() {
        Product product = new Product();
        product.setPrice(500);
        product.setId(1L);
        product.setName("Intel i5");
        Mockito.when(productService.checkIfProductExists(1L)).thenReturn(product);
        cartService.removeProductFromCart(1L, new HashMap<>());
    }
}
