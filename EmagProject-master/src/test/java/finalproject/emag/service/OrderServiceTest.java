package finalproject.emag.service;

import finalproject.emag.model.dto.CartProductDto;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.repository.OrderRepository;
import finalproject.emag.model.repository.OrderedProductRepository;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.util.PasswordEncoder;
import finalproject.emag.util.exception.EmptyCartException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderedProductRepository orderedProductRepository;

    @Mock
    private ProductRepository productRepository;

    private static User user = new User(1L, "gosho@abv.bg", PasswordEncoder.hashPassword("12345678"), "Gosho" );

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void makeOrder() {
        CartProductDto cartProductDto = CartProductDto.builder().id(1L).name("Intel i5").price(500).build();
        HashMap<CartProductDto, Integer> cart = new HashMap<>();
        cart.put(cartProductDto, 3);
        orderService.makeOrder(user, cart);
    }

    @Test(expected = EmptyCartException.class)
    public void makeOrderEmptyCart() {
        orderService.makeOrder(user, null);
    }
}
