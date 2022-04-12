package jp.co.rakuten.ecommerce.api.service;

import jp.co.rakuten.ecommerce.api.entity.CartItem;
import jp.co.rakuten.ecommerce.api.exception.NotFoundException;
import jp.co.rakuten.ecommerce.api.repository.CartRepository;
import jp.co.rakuten.ecommerce.common.dto.CartItemDto;
import jp.co.rakuten.ecommerce.common.dto.CartItemListDto;
import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {

    private CartRepository cartRepository;

    public CartItemListDto getCartItems(Integer customer_id) {

        List<CartItem> cartItems = cartRepository.findByCustomerId(customer_id);

        if (cartItems.isEmpty()) {
            throw new NotFoundException("CartItem not found for customerId=" + customer_id);
        }

        CartItemListDto cartItemListDto = CartItemListDto.builder().build();

        cartItems.forEach(item -> cartItemListDto.addCartItem(getCartItemDto(item)));

        return cartItemListDto;
    }

    public CartItemDto getCartItemByItemId(Integer item_id) {
        Optional<CartItem> cartItem = cartRepository.findByItemId(item_id);

        if (cartItem.isEmpty()) {
            throw new NotFoundException("CartItem not found for itemId=" + item_id);
        }

        return getCartItemDto(cartItem.get());
    }

    @Transactional
    public IdDto createCartItem(CartItemDto itemDto) {
        CartItem item = getCartItem(itemDto);
        item = cartRepository.save(item);
        return IdDto.builder().id(item.getId()).build();
    }

    @Transactional
    public MessageDto updateCartItem(CartItemDto itemDto) {
        CartItem item = getCartItem(itemDto);
        if (item.getQuantity()!=0) cartRepository.save(item);
        else cartRepository.deleteByItemId(item.getItemId());
        return MessageDto.builder().message("Success").build();
    }

    @Transactional
    public MessageDto deleteCartItem(Integer user_id) {
        cartRepository.deleteAllByCustomerId(user_id);
        return MessageDto.builder().message("Success").build();
    }

    private CartItemDto getCartItemDto(CartItem item) {
        return CartItemDto.builder().id(item.getId())
                .customerId(item.getCustomerId())
                .itemId(item.getItemId())
                .name(item.getName())
                .description(item.getDescription())
                .photoAddress(item.getPhotoAddress())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }

    private CartItem getCartItem(CartItemDto itemDto) {
        CartItem item = new CartItem();
        item.setId(itemDto.getId());
        item.setCustomerId(itemDto.getCustomerId());
        item.setItemId(itemDto.getItemId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPhotoAddress(itemDto.getPhotoAddress());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        return item;
    }
}
