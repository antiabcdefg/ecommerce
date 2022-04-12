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
public class OrderDetailListDto {

    private List<OrderDetailDto> orderDetailDtos;

    public void addOrderDetail(OrderDetailDto orderDto) {
        if (orderDetailDtos == null) {
            orderDetailDtos = new ArrayList<>();
        }
        orderDetailDtos.add(orderDto);
    }
}
