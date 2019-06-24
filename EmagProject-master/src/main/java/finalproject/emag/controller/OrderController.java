package finalproject.emag.controller;

import finalproject.emag.model.dto.CartProductDto;
import finalproject.emag.model.entity.User;
import finalproject.emag.model.messages.MessageSuccess;
import finalproject.emag.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequestMapping(produces = "application/json")
public final class OrderController extends BaseController {

    private static final String CART = "cart";
    private static final String USER = "user";

    @Autowired
    private OrderService orderService;

    @PostMapping(value = ("/view/cart/order"))
    @Transactional
    public MessageSuccess makeOrder(HttpServletRequest request) {
        validateLogin(request.getSession());
        User user = (User) request.getSession().getAttribute(USER);
        orderService.makeOrder(user, (HashMap<CartProductDto, Integer>) request.getSession().getAttribute(CART));
        request.getSession().setAttribute(CART, null);
        return new MessageSuccess("Your order was successful!", LocalDateTime.now());
    }
}
