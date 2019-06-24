package finalproject.emag.service;

import finalproject.emag.model.dto.PromotionProductDto;
import finalproject.emag.model.dto.RemovePromotionDto;
import finalproject.emag.model.entity.Discount;
import finalproject.emag.model.entity.Product;
import finalproject.emag.model.repository.DiscountRepository;
import finalproject.emag.model.repository.ProductRepository;
import finalproject.emag.util.DiscountNotify;
import finalproject.emag.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public final class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiscountNotify discountNotify;

    @Transactional
    public void addDiscount(PromotionProductDto product, Long productId) {
        Product discounted = productRepository.findById(productId).get();
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
    public void removeDiscount(RemovePromotionDto promo) {
        Product product = productRepository.findById(promo.getProductId()).get();
        Discount discount  = discountRepository.findByProductId(promo.getProductId()).get();
        product.setPrice(discount.getOldPrice());
        productRepository.save(product);
        discountRepository.delete(discount);
    }
}
