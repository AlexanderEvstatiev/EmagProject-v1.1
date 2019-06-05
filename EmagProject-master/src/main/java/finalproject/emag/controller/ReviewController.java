package finalproject.emag.controller;

import finalproject.emag.model.dto.ReviewRequestDto;
import finalproject.emag.model.pojo.entity.User;
import finalproject.emag.model.pojo.messages.MessageSuccess;
import finalproject.emag.model.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/products",produces = "application/json")
public class ReviewController extends BaseController{

    private static final String USER = "user";

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/{id}")
    public MessageSuccess addReview(@RequestBody ReviewRequestDto review,
                            @PathVariable("id")Long productId, HttpServletRequest request) {
        validateLogin(request.getSession());
        User user = (User) request.getSession().getAttribute(USER);
        this.reviewService.addReview(review, productId, user);
        return new MessageSuccess("Review added.", LocalDateTime.now());
    }

    @DeleteMapping(value = "/{id}")
    public MessageSuccess deleteReview(@PathVariable("id")Long productId, HttpServletRequest request) {
        validateLogin(request.getSession());
        User user = (User)request.getSession().getAttribute("user");
        this.reviewService.deleteReview(productId, user);
        return new MessageSuccess("Review deleted.", LocalDateTime.now());
    }

    @PutMapping(value = "/{id}")
    public MessageSuccess editReview(@RequestBody ReviewRequestDto review,
                             @PathVariable("id")Long productId, HttpServletRequest request) {
        validateLogin(request.getSession());
        User user = (User) request.getSession().getAttribute(USER);
        this.reviewService.editReview(review, productId, user);
        return new MessageSuccess("Review edited.", LocalDateTime.now());
    }
}