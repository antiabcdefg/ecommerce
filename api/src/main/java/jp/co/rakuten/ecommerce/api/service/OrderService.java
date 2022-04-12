package jp.co.rakuten.ecommerce.api.service;

import jp.co.rakuten.ecommerce.api.entity.Order;
import jp.co.rakuten.ecommerce.api.entity.OrderDetail;
import jp.co.rakuten.ecommerce.api.exception.NotFoundException;
import jp.co.rakuten.ecommerce.api.repository.OrderDetailRepository;
import jp.co.rakuten.ecommerce.api.repository.OrderRepository;
import jp.co.rakuten.ecommerce.common.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    public OrderListDto getOrders() {
        List<Order> orderList = orderRepository.findAll();

        OrderListDto orderListDto = OrderListDto.builder().build();
        for (Order o : orderList) {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(o.getId());
            orderListDto.addOrder(getOrderDto(o, getOrderListDetailDto(orderDetailList)));
        }

        return orderListDto;
    }

    public OrderDetailListDto getOrdersHot() {
        List<OrderDetail> orderDetailList = orderDetailRepository.findAll();

        if (orderDetailList.isEmpty()) {
            throw new NotFoundException("OrderDetail not found");
        }
        return getOrderDetailListDto2(orderDetailList);
    }

    private OrderDetailListDto getOrderDetailListDto2(List<OrderDetail> orderDetailList) {
        OrderDetailListDto orderDetailListDto = OrderDetailListDto.builder().build();
        for (OrderDetail o : orderDetailList) orderDetailListDto.addOrderDetail(getOrderDetailDto(o));
        return orderDetailListDto;
    }

    public OrderListDto getOrderByCustomerId(Integer customer_id) {
        List<Order> orderList = orderRepository.findByCustomerId(customer_id);

        if (orderList.isEmpty()) {
            throw new NotFoundException("Order not found for customerId=" + customer_id);
        }

        OrderListDto orderListDto = OrderListDto.builder().build();
        for (Order o : orderList) {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(o.getId());
            orderListDto.addOrder(getOrderDto(o, getOrderListDetailDto(orderDetailList)));
        }

        return orderListDto;
    }

    public OrderDto getOrderById(Integer id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(id);

        if (orderOpt.isEmpty() || orderDetailList.isEmpty()) {
            throw new NotFoundException("Order not found for id=" + id);
        }

        return getOrderDto(orderOpt.get(), getOrderListDetailDto(orderDetailList));
    }

    @Transactional
    public IdDto createOrderByCartItem(List<CartItemDto> cartItemListDto) {
        Order order = getOrderFromCartItem(cartItemListDto);
        orderRepository.save(order);

        for (CartItemDto cartItemDto : cartItemListDto) {
            OrderDetail orderDetail = getOrderDetailFromCartItem(order, cartItemDto);
            orderDetailRepository.save(orderDetail);
        }

        return IdDto.builder().id(order.getId()).build();
    }

    @Transactional
    public IdDto createOrder(OrderDto orderDto) {
        Order order = getOrder(orderDto);
        orderRepository.save(order);

        for (OrderDetailDto orderDetailDto : orderDto.getItems()) {
            OrderDetail orderDetail = getOrderDetail(orderDetailDto);
            orderDetailRepository.save(orderDetail);
        }

        return IdDto.builder().id(order.getId()).build();
    }

    @Transactional
    public MessageDto updateOrder(OrderDto orderDto) {
        Order order = getOrder(orderDto);
        orderRepository.save(order);

        for (OrderDetailDto orderDetailDto : orderDto.getItems()) {
            OrderDetail orderDetail = getOrderDetail(orderDetailDto);
            orderDetailRepository.save(orderDetail);
        }
        return MessageDto.builder().message("Success").build();
    }

    private OrderDto getOrderDto(Order order, List<OrderDetailDto> details) {
        return OrderDto.builder().id(order.getId()).customerId(order.getCustomerId()).status(order.getStatus()).
                createdAt(order.getCreatedAt()).items(details).
                build();
    }

    private OrderDetailDto getOrderDetailDto(OrderDetail detail) {
        return OrderDetailDto.builder().id(detail.getId()).orderId(detail.getOrderId()).itemId(detail.getItemId()).
                name(detail.getName()).
                quantity(detail.getQuantity()).
                price(detail.getPrice()).
                photoAddress(detail.getPhotoAddress()).
                build();

    }

    private List<OrderDetailDto> getOrderListDetailDto(List<OrderDetail> details) {
        List<OrderDetailDto> orderDetailOptList = new ArrayList<>();

        for (OrderDetail o : details)
            orderDetailOptList.add(OrderDetailDto.builder().id(o.getId()).orderId(o.getOrderId()).itemId(o.getItemId()).
                    name(o.getName()).
                    quantity(o.getQuantity()).
                    price(o.getPrice()).
                    photoAddress(o.getPhotoAddress()).
                    build());

        return orderDetailOptList;
    }

    private Order getOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCustomerId(orderDto.getCustomerId());
        order.setStatus(orderDto.getStatus());
        order.setCreatedAt(new Date());
        return order;
    }

    private OrderDetail getOrderDetail(OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailDto.getId());
        orderDetail.setOrderId(orderDetailDto.getOrderId());
        orderDetail.setItemId(orderDetailDto.getItemId());
        orderDetail.setName(orderDetailDto.getName());
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setPrice(orderDetailDto.getPrice());
        orderDetail.setPhotoAddress(orderDetailDto.getPhotoAddress());
        return orderDetail;
    }

    private Order getOrderFromCartItem(List<CartItemDto> cartItemListDto) {
        Order order = new Order();
        order.setCustomerId(cartItemListDto.get(0).getCustomerId());
        order.setStatus("NEW");
        order.setCreatedAt(new Date());
        return order;
    }

    private OrderDetail getOrderDetailFromCartItem(Order order, CartItemDto cartItemDto) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order.getId());
        orderDetail.setItemId(cartItemDto.getItemId());
        orderDetail.setName(cartItemDto.getName());
        orderDetail.setQuantity(cartItemDto.getQuantity());
        orderDetail.setPrice(cartItemDto.getPrice());
        orderDetail.setPhotoAddress(cartItemDto.getPhotoAddress());
        return orderDetail;
    }

}
