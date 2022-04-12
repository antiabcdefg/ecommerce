package jp.co.rakuten.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDto {

    private List<OrderDto> orders;

    public void addOrder(OrderDto orderDto) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(orderDto);
    }
}
