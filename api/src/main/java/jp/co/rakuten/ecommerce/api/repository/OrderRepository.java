package jp.co.rakuten.ecommerce.api.repository;

import jp.co.rakuten.ecommerce.api.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAll();
    List<Order> findByCustomerId(Integer customer_id);
}
