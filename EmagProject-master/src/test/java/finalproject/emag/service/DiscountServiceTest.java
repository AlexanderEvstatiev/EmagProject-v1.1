package finalproject.emag.service;

import finalproject.emag.model.dto.DiscountProductDto;
import finalproject.emag.model.entity.Category;
import finalproject.emag.model.entity.Discount;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.repository.DiscountRepository;
import finalproject.emag.util.GetDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;

    @MockBean
    private DiscountRepository discountRepository;

    @Before
    public void setUp() {
        Discount discount = new Discount();
        discount.setId(1);
        discount.setEndDate(GetDate.getDate("12/10/2015"));
        discount.setStartDate(GetDate.getDate("12/11/2015"));
        discount.setOldPrice(5000);
        discount.setNewPrice(4000);
        Product product = new Product(1, new Category(1, "some"), "intel", 2000, 50, null);
        discount.setProduct(product);
        Mockito.when(discountRepository.findById()).thenReturn(discount);
    }

}
