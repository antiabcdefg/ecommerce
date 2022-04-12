package jp.co.rakuten.ecommerce.api.service;

import jp.co.rakuten.ecommerce.api.entity.Item;
import jp.co.rakuten.ecommerce.api.exception.NotFoundException;
import jp.co.rakuten.ecommerce.api.repository.ItemRepository;
import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.ItemDto;
import jp.co.rakuten.ecommerce.common.dto.ItemListDto;
import jp.co.rakuten.ecommerce.common.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ItemService {

    private ItemRepository itemRepository;

    public ItemListDto getItems() {
        Iterable<Item> itemList = itemRepository.findAll();

        ItemListDto itemListDto = ItemListDto.builder().build();

        itemList.forEach(item -> itemListDto.addItem(getItemDto(item)));

        return itemListDto;
    }

    public ItemListDto getItemsByName(String name) {
        Iterable<Item> itemList = itemRepository.findItemByNameContainsIgnoreCase(name);

        ItemListDto itemListDto = ItemListDto.builder().build();

        itemList.forEach(item -> itemListDto.addItem(getItemDto(item)));

        return itemListDto;
    }

    public ItemDto getItemById(Integer id) {
        Optional<Item> itemOpt = itemRepository.findById(id);

        if (itemOpt.isEmpty()) {
            throw new NotFoundException("Item not found for id=" + id);
        }

        return getItemDto(itemOpt.get());
    }

    @Transactional
    public IdDto createItem(ItemDto itemDto) {
        Item item = getItem(itemDto);
        item = itemRepository.save(item);
        return IdDto.builder().id(item.getId()).build();
    }

    @Transactional
    public MessageDto updateItem(ItemDto itemDto) {
        Item item = getItem(itemDto);
        itemRepository.save(item);
        return MessageDto.builder().message("Success").build();
    }

    private ItemDto getItemDto(Item item) {
        return ItemDto.builder().itemId(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .photoAddress(item.getPhotoAddress())
                .price(item.getPrice())
                .inventoryCount(item.getInventoryCount())
                .enabled(item.isEnabled())
                .createdAt(item.getCreatedAt())
                .build();
    }

    private Item getItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getItemId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPhotoAddress(itemDto.getPhotoAddress());
        item.setPrice(itemDto.getPrice());
        item.setInventoryCount(itemDto.getInventoryCount());
        item.setEnabled(itemDto.isEnabled());
        item.setCreatedAt(new Date());
        return item;
    }
}
