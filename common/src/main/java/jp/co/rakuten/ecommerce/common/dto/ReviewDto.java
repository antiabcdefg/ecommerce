package jp.co.rakuten.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Data transfer object for Item class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Integer id;

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer itemId;

    @NotNull
    private Integer orderId;

    @NotNull()
    private Double rating;

    @NotBlank()
    private String review;

    @NotNull
    private String cusName;


}
