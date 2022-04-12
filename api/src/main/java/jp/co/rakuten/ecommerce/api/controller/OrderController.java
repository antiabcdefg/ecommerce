package jp.co.rakuten.ecommerce.api.controller;

import io.swagger.annotations.Api;
import jp.co.rakuten.ecommerce.api.service.OrderService;
import jp.co.rakuten.ecommerce.common.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="OrderController")
@RestController
@AllArgsConstructor
public class OrderController {

    OrderService orderService;

    @GetMapping("/orders")
    public OrderListDto getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/orders/all")
    public OrderDetailListDto getOrdersHot() {
        return orderService.getOrdersHot();
    }

    @GetMapping("/orders/{id}")
    public OrderDto getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/orders/user/{customer_id}")
    public OrderListDto getOrderByCustomerId(@PathVariable Integer customer_id) {
        return orderService.getOrderByCustomerId(customer_id);
    }

    @PostMapping("/orders/cart")
    public IdDto createOrderByCartItem(@Validated @RequestBody List<CartItemDto> cartItemListDto) {
        return orderService.createOrderByCartItem(cartItemListDto);
    }

    @PostMapping("/orders")
    public IdDto createOrder(@Validated @RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @PutMapping("/orders")
    public MessageDto updateOrder(@Validated @RequestBody OrderDto orderDto) {
        return orderService.updateOrder(orderDto);
    }
}
