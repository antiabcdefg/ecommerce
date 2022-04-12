package jp.co.rakuten.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;

    @NotNull
    private Integer customerId;

    @NotBlank
    private String status;

    private Date createdAt;

    @NotNull
    private List<OrderDetailDto> items;

}
