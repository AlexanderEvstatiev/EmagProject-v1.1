package finalproject.emag.controller;

import finalproject.emag.model.dto.GlobalViewProductDto;
import finalproject.emag.service.ProductService;
import finalproject.emag.service.ProductServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void findAll() throws Exception {
        GlobalViewProductDto product = new GlobalViewProductDto();
        product.setId(1L);
        product.setName("Intel");
        product.setPrice(500);
        product.setQuantity(10);
        product.setReviewsGrade(0);
        product.setReviewsCount(0);

        ArrayList<GlobalViewProductDto> products = new ArrayList<>();
        products.add(product);
        given(productService.getAllProducts()).willReturn(products);

        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1,'name': 'Intel','price': 500," +
                                " 'quantity': 10, 'reviewsCount': 0, 'reviewsGrade': 0}]"));
    }

    @Test
    public void findAllEmpty() throws Exception {
        given(productService.getAllProducts()).willReturn(new ArrayList<>());
        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void findAllFiltered() throws Exception {
        GlobalViewProductDto product = new GlobalViewProductDto();
        product.setId(1L);
        product.setName("Intel");
        product.setPrice(500);
        product.setQuantity(10);
        product.setReviewsGrade(0);
        product.setReviewsCount(0);

        ArrayList<GlobalViewProductDto> products = new ArrayList<>();
        products.add(product);
        given(productService.getAllProductsFiltered("ASC", 0, 1000)).willReturn(products);

        this.mockMvc.perform(get("/products/filter").param("order", "ASC")
                .param("from", "0").param("to", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': 1,'name': 'Intel','price': 500," +
                        " 'quantity': 10, 'reviewsCount': 0, 'reviewsGrade': 0}]"));
    }
}
