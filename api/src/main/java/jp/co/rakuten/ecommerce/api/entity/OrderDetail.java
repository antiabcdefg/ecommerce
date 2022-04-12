package jp.co.rakuten.ecommerce.api.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer orderId;
    private Integer itemId;
    private String name;
    private Integer quantity;
    private Double price;
    private String photoAddress;

    @OneToOne
    @JoinColumn(name = "orderId", updatable = false,insertable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "itemId", updatable = false,insertable = false)
    private Item item;
}
