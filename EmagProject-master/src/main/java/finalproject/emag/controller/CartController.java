package finalproject.emag.controller;

import finalproject.emag.model.dto.CartProductDto;
import finalproject.emag.model.dto.CartViewProductDto;
import finalproject.emag.model.dto.ProductViewDto;
import finalproject.emag.model.pojo.entity.Product;
import finalproject.emag.model.service.CartService;
import finalproject.emag.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(produces = "application/json")
public class CartController extends BaseController {

    private static final String CART = "cart";

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @PostMapping(value = ("/products/{id}/add"))
    public ProductViewDto addToCart(@PathVariable("id") long productId, HttpServletRequest request) {
        validateLogin(request.getSession());
        if (request.getSession().getAttribute(CART) == null ) {
            request.getSession().setAttribute(CART, new HashMap<Product, Integer>());
        }
        cartService.addProductToCart(productId,
                    (HashMap<CartProductDto, Integer>) request.getSession().getAttribute(CART));
        return productService.getProductById(productId);
    }

    @GetMapping(value = ("/view/cart"))
    public ArrayList<CartViewProductDto> viewCart(HttpServletRequest request) {
        validateLogin(request.getSession());
        if (request.getSession().getAttribute(CART) != null) {
            return cartService.viewCart((HashMap<CartProductDto, Integer>) request.getSession().getAttribute(CART));
        }
        return new ArrayList<>();
    }

    @DeleteMapping(value = ("/products/{id}/remove"))
    public ArrayList<CartViewProductDto> removeProductFromCart(
            @PathVariable("id") long productId, HttpServletRequest request) {
        validateLogin(request.getSession());
        if (request.getSession().getAttribute(CART) != null ) {
            HashMap<CartProductDto, Integer> cart =
                    (HashMap<CartProductDto, Integer>) request.getSession().getAttribute(CART);
            cartService.removeProductFromCart(productId, cart);
            if (cart.size() > 0) {
                return cartService.viewCart(cart);
            } else {
                request.getSession().setAttribute(CART, null);
            }
        }
        return new ArrayList<>();
    }
}
