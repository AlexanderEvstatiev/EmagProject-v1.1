package finalproject.emag.controller;

import finalproject.emag.model.entity.User;
import finalproject.emag.model.messages.MessageSuccess;
import finalproject.emag.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/images",produces = "application/json")
public final class ImageController extends BaseController{

    @Autowired
    private ImageService imageService;

    @PostMapping("/users")
    public MessageSuccess uploadUserImage(@RequestBody String input, HttpSession session){
        validateLogin(session);
        User user = (User) session.getAttribute("user");
        this.imageService.uploadUserImage(user,input);
        return new MessageSuccess("Image upload successful", LocalDateTime.now());
    }

    @PostMapping("/products/{id}")
    public MessageSuccess uploadProductImage(
            @RequestBody String input, @PathVariable("id") long productId, HttpSession session){
        validateLoginAdmin(session);
        this.imageService.uploadProductImage(productId,input);
        return new MessageSuccess("Image upload successful", LocalDateTime.now());
    }

    @GetMapping(value = "/users/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] downloadUserImage(@PathVariable("id") long userId) {
        return this.imageService.getUserImage(userId);
    }

    @GetMapping(value = "/products/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] downloadProductImage(@PathVariable("id") long productId) {
        return  this.imageService.getProductImage(productId);
    }
}