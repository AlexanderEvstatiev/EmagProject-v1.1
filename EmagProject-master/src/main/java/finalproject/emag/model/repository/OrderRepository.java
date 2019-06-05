package finalproject.emag.model.repository;

import finalproject.emag.model.pojo.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
