package jp.co.rakuten.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    private Integer id;

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer itemId;

    @NotNull
    private Integer quantity;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private String photoAddress;

}
