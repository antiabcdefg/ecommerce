package jp.co.rakuten.ecommerce.api.repository;

import jp.co.rakuten.ecommerce.api.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findItemByCustomerIdAndItemId(Integer cus_id, Integer item_id);
}
