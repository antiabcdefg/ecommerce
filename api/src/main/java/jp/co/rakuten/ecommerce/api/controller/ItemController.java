package jp.co.rakuten.ecommerce.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.rakuten.ecommerce.api.service.ItemService;
import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.ItemDto;
import jp.co.rakuten.ecommerce.common.dto.ItemListDto;
import jp.co.rakuten.ecommerce.common.dto.MessageDto;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags="ItemController")
@RestController
@AllArgsConstructor
public class ItemController {

    ItemService itemService;

    @ApiOperation("List of items")
    @GetMapping("/items")
    public ItemListDto getItems() {
        return itemService.getItems();
    }

    @GetMapping("/item/{id}")
    public ItemDto getItem(@PathVariable Integer id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/items/{name}")
    public ItemListDto getItemsByName(@PathVariable String name) {
        return itemService.getItemsByName(name);
    }

    @PostMapping("/items")
    public IdDto createItem(@Validated @RequestBody ItemDto itemDto) {
        return itemService.createItem(itemDto);
    }

    @PutMapping("/items")
    public MessageDto updateItem(@Validated @RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemDto);
    }

}
