package finalproject.emag.model.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.emag.model.pojo.entity.Product;
import finalproject.emag.model.pojo.entity.User;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.model.repository.UserRepository;
import finalproject.emag.util.exception.ImageMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {

    private static final String IMAGE_PATH = "C:\\Users\\Aleksandar_Evstatiev\\Desktop\\images";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void uploadProductImage(Long productId,String input) throws Exception{
        Product product = productRepository.findById(productId).get();
        JsonNode jsonNode = objectMapper.readTree(input);
        String imageUrl = uploadImage(jsonNode,product.getId());
        product.setImageUrl(imageUrl);
        productRepository.save(product);
    }

    @Transactional
    public void uploadUserImage(User user, String input) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(input);
        String imageUrl = uploadImage(jsonNode,user.getId());
        user.setImageUrl(imageUrl);
        this.userRepository.save(user);
    }

    public byte[] getUserImage(long userId) {
        String imageUrl = getUserImageUrl(userId);
        return getImage(imageUrl);
    }

    public byte[] getProductImage(long productId) {
        String imageUrl = getProductImageUrl(productId);
        return getImage(imageUrl);
    }

    private byte[] getImage(String imageUrl) {
        try {
            File image = new File(IMAGE_PATH + imageUrl);
            FileInputStream fis = new FileInputStream(image);
            return fis.readAllBytes();
        } catch (IOException e) {
            throw new ImageMissingException();
        }
    }

    private String getUserImageUrl(long userId) {
        User user = userRepository.findById(userId).get();
        return user.getImageUrl();
    }

    private String getProductImageUrl(long productId) {
        Product product = productRepository.findById(productId).get();
        return product.getImageUrl();
    }

    private String uploadImage(JsonNode jsonNode,long id) throws IOException {
        String base64 = jsonNode.get("image_url").textValue();
        byte[] bytes = Base64.getDecoder().decode(base64);
        String name = id + Math.round(Math.random()) + System.currentTimeMillis() + ".png";
        File image = new File(IMAGE_PATH +name);
        FileOutputStream fos = new FileOutputStream(image);
        fos.write(bytes);
        fos.close();
        return name;
    }
}