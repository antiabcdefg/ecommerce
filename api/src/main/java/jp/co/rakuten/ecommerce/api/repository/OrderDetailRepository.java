package jp.co.rakuten.ecommerce.api.repository;

import jp.co.rakuten.ecommerce.api.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
    List<OrderDetail> findAll();
    List<OrderDetail> findByOrderId(Integer orderId);
}
