package finalproject.emag.controller;

import finalproject.emag.model.dto.PromotionProductDto;
import finalproject.emag.model.dto.RemovePromotionDto;
import finalproject.emag.model.pojo.messages.MessageSuccess;
import finalproject.emag.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping(produces = "application/json")
public class DiscountController extends BaseController {

    @Autowired
    private DiscountService discountService;

    @PostMapping(value = "/discounts/{id}")
    public MessageSuccess addDiscount(@PathVariable("id") long productId, @RequestBody PromotionProductDto product,
                                      HttpServletRequest request) {
        validateLoginAdmin(request.getSession());
        discountService.addDiscount(product, productId);
        return new MessageSuccess("Discount added!", LocalDateTime.now());
    }

    @DeleteMapping(value = "/discounts/{id}")
    public MessageSuccess removeDiscount(@PathVariable("id") long productId, HttpServletRequest request){
        validateLoginAdmin(request.getSession());
        RemovePromotionDto product = new RemovePromotionDto(productId);
        discountService.removeDiscount(product);
        return new MessageSuccess("Discount removed!", LocalDateTime.now());
    }
}