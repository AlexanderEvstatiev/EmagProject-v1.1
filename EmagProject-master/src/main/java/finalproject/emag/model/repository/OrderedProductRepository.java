package finalproject.emag.model.repository;

import finalproject.emag.model.entity.OrderedProduct;
import finalproject.emag.model.entity.keys.OrderedProductId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderedProductRepository extends PagingAndSortingRepository<OrderedProduct, OrderedProductId> {

}
