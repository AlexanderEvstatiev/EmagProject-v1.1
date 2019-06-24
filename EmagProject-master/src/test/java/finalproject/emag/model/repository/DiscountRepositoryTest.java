package finalproject.emag.model.repository;

import finalproject.emag.model.entity.Category;
import finalproject.emag.model.entity.Discount;
import finalproject.emag.model.entity.Product;
import finalproject.emag.util.GetDate;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class DiscountRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DiscountRepository discountRepository;

    @Test
    public void testFindDiscount() {
        Discount discount = new Discount();
        discount.setId(1);
        discount.setEndDate(GetDate.getDate("12/10/2015"));
        discount.setStartDate(GetDate.getDate("12/11/2015"));
        discount.setOldPrice(5000);
        discount.setNewPrice(4000);
        Product product = new Product(1, new Category(1, "some"), "intel", 2000, 50, null);
        discount.setProduct(product);
        testEntityManager.persist(product);
        testEntityManager.persist(discount);
        testEntityManager.flush();
        Discount savedDiscount = discountRepository.findByProductId(product.getId()).get();
        Assert.assertEquals(discount, savedDiscount);
    }
}
