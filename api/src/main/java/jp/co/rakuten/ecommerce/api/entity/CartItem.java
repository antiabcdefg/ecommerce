package jp.co.rakuten.ecommerce.api.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;
    private Integer itemId;
    private String name;
    private String description;
    private Double price;
    private String photoAddress;
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "customerId", updatable = false,insertable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "itemId", updatable = false,insertable = false)
    private Item item;
}
