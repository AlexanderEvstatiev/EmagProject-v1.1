package finalproject.emag.service;

import finalproject.emag.model.dto.CartProductDto;
import finalproject.emag.model.dto.CartViewProductDto;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private ProductService productService;

    public ArrayList<CartViewProductDto> viewCart(HashMap<CartProductDto, Integer> cart) {
        ArrayList<CartViewProductDto> userCart = new ArrayList<>();
        for (Map.Entry<CartProductDto, Integer> e : cart.entrySet()) {
            CartViewProductDto product = CartViewProductDto.builder().id(e.getKey().getId())
                    .name(e.getKey().getName())
                    .quantity(e.getValue())
                    .price(e.getKey().getPrice() * e.getValue()).build();
            userCart.add(product);
        }
        return userCart;
    }

    public void addProductToCart(long productId, HashMap<CartProductDto, Integer> cart) {
        CartProductDto product = getProductForCart(productId);
        if(cart.containsKey(product)) {
            int quantity = cart.get(product);
            cart.put(product, quantity+1);
        }
        else {
            cart.put(product, 1);
        }
    }

    public void removeProductFromCart(long productId, HashMap<CartProductDto, Integer> cart) {
        CartProductDto product = getProductForRemove(productId);
        if(cart.containsKey(product)) {
            int quantity = cart.get(product);
            if (quantity > 1) {
                cart.put(product, quantity - 1);
            } else if (quantity == 1) {
                cart.remove(product);
            }
        } else {
            throw new BaseException("No such product in cart!");
        }
    }

    private CartProductDto getProductForCart(long productId) {
        Product product = productService.checkIfProductExists(productId);
        productService.checkProductQuantity(productId, 1);
        return getProduct(product);
    }

    private CartProductDto getProductForRemove(long productId) {
        Product product = productService.checkIfProductExists(productId);
        return getProduct(product);
    }

    private CartProductDto getProduct(Product product) {
        return CartProductDto.builder().
                id(product.getId()).name(product.getName()).price(product.getPrice()).build();
    }
}