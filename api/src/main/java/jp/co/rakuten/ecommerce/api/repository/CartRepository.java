package jp.co.rakuten.ecommerce.api.repository;


import jp.co.rakuten.ecommerce.api.entity.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends CrudRepository<CartItem, Integer> {
    Optional<CartItem> findByItemId(Integer item_id);
    List<CartItem> findByCustomerId(Integer user_id);
    Optional<CartItem> deleteByItemId(Integer item_id);
    Optional<CartItem> deleteAllByCustomerId(Integer user_id);
}
