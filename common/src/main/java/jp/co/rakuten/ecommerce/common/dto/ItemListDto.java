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
public class ItemListDto {

    private List<ItemDto> items;

    public void addItem(ItemDto itemDto) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(itemDto);
    }
}
