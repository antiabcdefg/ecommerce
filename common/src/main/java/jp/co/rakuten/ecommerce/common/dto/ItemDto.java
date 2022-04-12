package jp.co.rakuten.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Data transfer object for Item class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    @NotNull
    private Integer itemId;

    @NotBlank()
    private String name;

    @NotBlank()
    private String description;

    @NotBlank()
    private String photoAddress;

    @NotNull()
    private Double price;

    @NotNull()
    private Integer inventoryCount;

    @NotNull()
    private boolean enabled;

    private Date createdAt;
}
