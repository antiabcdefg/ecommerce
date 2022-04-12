package jp.co.rakuten.ecommerce.api.repository;

import jp.co.rakuten.ecommerce.api.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
}
