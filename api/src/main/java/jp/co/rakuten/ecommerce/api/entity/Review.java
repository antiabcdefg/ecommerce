package jp.co.rakuten.ecommerce.api.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;
    private Integer itemId;
    private Integer orderId;
    private Double rating;
    private String review;
    private String firstName;
    private String lastName;

    @OneToOne
    @JoinColumn(name = "customerId", updatable = false, insertable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "itemId", updatable = false, insertable = false)
    private Item item;

    @OneToOne
    @JoinColumn(name = "orderId", updatable = false, insertable = false)
    private Order order;
}
