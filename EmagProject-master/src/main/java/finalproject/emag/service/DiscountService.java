package finalproject.emag.service;

import finalproject.emag.model.dto.DiscountProductDto;
import finalproject.emag.model.entity.Discount;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.repository.DiscountRepository;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.util.DiscountNotify;
import finalproject.emag.util.GetDate;
import finalproject.emag.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private DiscountNotify discountNotify;

    @Transactional
    public void addDiscount(DiscountProductDto product, long productId) {
        Product discounted = productService.checkIfProductExists(productId);
        double oldPrice = discounted.getPrice();
        Discount discount = Discount.builder().product(discounted).endDate(GetDate.getDate(product.getEndDate()))
                .startDate(GetDate.getDate(product.getStartDate())).newPrice(product.getNewPrice())
                .oldPrice(oldPrice).build();
        discountRepository.save(discount);
        discounted.setPrice(product.getNewPrice());
        productRepository.save(discounted);
        discountNotify.notifyForPromotion("Promotion on "+discounted.getName(),
                "We have a new special offer on " +
                discounted.getName() + " from " + oldPrice+" to "+product.getNewPrice());
    }

    @Transactional
    public void removeDiscount(long productId) {
        Product product = productService.checkIfProductExists(productId);
        Optional<Discount> savedDiscount  = discountRepository.findByProductId(productId);
        if (savedDiscount.isPresent()) {
            Discount discount = savedDiscount.get();
            product.setPrice(discount.getOldPrice());
            productRepository.save(product);
            discountRepository.delete(discount);
        }
        else throw new BaseException("No such discount!");
    }
}
