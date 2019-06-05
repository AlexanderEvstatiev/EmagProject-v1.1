package finalproject.emag.model.repository;

import finalproject.emag.model.pojo.composite.keys.OrderedProductId;
import finalproject.emag.model.pojo.entity.OrderedProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderedProductRepository extends PagingAndSortingRepository<OrderedProduct, OrderedProductId> {

}
