package finalproject.emag.service;

import finalproject.emag.model.dto.CartProductDto;
import finalproject.emag.model.entity.keys.OrderedProductId;
import finalproject.emag.model.entity.Order;
import finalproject.emag.model.entity.OrderedProduct;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.repository.OrderRepository;
import finalproject.emag.model.repository.OrderedProductRepository;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.util.exception.EmptyCartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderedProductRepository orderedProductRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void makeOrder(User user, HashMap<CartProductDto, Integer> userCart) {
        double price = 0;
        if (userCart == null) {
            throw new EmptyCartException();
        }
        for (Map.Entry<CartProductDto, Integer> e : userCart.entrySet()) {
            productService.checkProductQuantity(e.getKey().getId(), e.getValue());
            price += (e.getKey().getPrice() * e.getValue());
        }
        Order order = insertOrder(user, price);
        insertOrderProducts(userCart, order);
        updateQuantity(userCart);
    }

    private Order insertOrder(User u, double price) {
        Order order = Order.builder().user(u).price(price).date(LocalDate.now()).build();
        return orderRepository.save(order);
    }

    private void insertOrderProducts(HashMap<CartProductDto, Integer> userCart, Order order) {
        for (Map.Entry<CartProductDto, Integer> e : userCart.entrySet()) {
            Product product = Product.builder().id(e.getKey().getId()).build();
            OrderedProductId id = new OrderedProductId(order, product);
            OrderedProduct orderedProduct = OrderedProduct.builder().id(id).quantity(e.getValue()).build();
            orderedProductRepository.save(orderedProduct);
        }
    }

    private void updateQuantity(HashMap<CartProductDto, Integer> userCart) {
        for (Map.Entry<CartProductDto, Integer> e : userCart.entrySet()) {
            productRepository.changeProductQuantityAfterOrder(e.getValue(), e.getKey().getId());
        }
    }
}
