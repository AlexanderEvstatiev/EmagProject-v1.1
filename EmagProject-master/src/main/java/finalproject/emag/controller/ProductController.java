package finalproject.emag.controller;

import finalproject.emag.model.dto.AddProductDto;
import finalproject.emag.model.dto.GlobalViewProductDto;
import finalproject.emag.model.dto.ProductViewDto;
import finalproject.emag.model.messages.MessageSuccess;
import finalproject.emag.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping(produces = "application/json")
@Validated
public class ProductController extends BaseController {

    private static final int MIN_NUMBER_OF_PRODUCTS = 0;
    private static final int MAX_NUMBER_OF_PRODUCTS = 9999;
    private static final String MIN_PRICE = "0";
    private static final String MAX_PRICE = "99999";

    @Autowired
    private ProductService productService;

    @GetMapping(value = ("/products"))
    public ArrayList<GlobalViewProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = ("/products/filter"))
    public ArrayList<GlobalViewProductDto> getAllProductsFiltered(
            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
            @RequestParam(value = "from", required = false, defaultValue = MIN_PRICE) Double min,
            @RequestParam(value = "to", required = false, defaultValue = MAX_PRICE) Double max) {
        return productService.getAllProductsFiltered(order, min, max);
    }

    @GetMapping(value = ("/products/subcategory/{id}"))
    public ArrayList<GlobalViewProductDto> getAllProductsBySubcategory(
            @PathVariable("id") long categoryId) {
        return productService.getAllProductsByCategory(categoryId);
    }

    @GetMapping(value = ("/products/subcategory/{id}/filter"))
    public ArrayList<GlobalViewProductDto> getAllProductsBySubcategoryFiltered(
            @PathVariable(value = "id") long id,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
            @RequestParam(value = "from", required = false, defaultValue = MIN_PRICE) double min,
            @RequestParam(value = "to", required = false, defaultValue = MAX_PRICE) double max) {
        return productService.getAllProductsByCategoryFiltered(id, order, min, max);
    }

    @GetMapping(value = ("/products/{id}"))
    public ProductViewDto getProductById(@PathVariable("id") long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping(value = ("/products/search/{name}"))
    public ArrayList<GlobalViewProductDto> searchProducts(@PathVariable("name") String name) {
        return productService.searchProducts(name);
    }

    @PostMapping(value = ("/products/add"))
    public MessageSuccess addProduct(@RequestBody AddProductDto product, HttpServletRequest request) {
        validateLoginAdmin(request.getSession());
        productService.insertProductInDB(product);
        return new MessageSuccess("You successfully added the product!", LocalDateTime.now());
    }

    @PutMapping(value = ("/products/{id}/quantity/{quantity}"))
    public MessageSuccess changeProductQuantity(
            @PathVariable("id") long productId,
            @Min(MIN_NUMBER_OF_PRODUCTS) @Max(MAX_NUMBER_OF_PRODUCTS) @PathVariable("quantity") Integer quantity,
            HttpServletRequest request) {
        validateLoginAdmin(request.getSession());
        productService.changeQuantity(productId, quantity);
        return new MessageSuccess("Product with id - " + productId + " now has quantity - " + quantity + ".",
                LocalDateTime.now());
    }

    @DeleteMapping(value = ("/products/{id}/delete"))
    public MessageSuccess deleteProduct(@PathVariable("id") long id, HttpServletRequest request) {
        validateLoginAdmin(request.getSession());
        productService.deleteProduct(id);
        return new MessageSuccess("The product with id - " + id + " has been removed.", LocalDateTime.now());
    }
}
