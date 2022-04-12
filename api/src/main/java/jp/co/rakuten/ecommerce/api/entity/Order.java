package jp.co.rakuten.ecommerce.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;
    private String status;
    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "customerId", updatable = false,insertable = false)
    private Customer customer;

}
