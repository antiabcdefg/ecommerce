package jp.co.rakuten.ecommerce.api.controller;

import io.swagger.annotations.Api;
import jp.co.rakuten.ecommerce.api.service.CartService;
import jp.co.rakuten.ecommerce.common.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags="CartController")
@RestController
@AllArgsConstructor
@CrossOrigin(value = "http://localhost:18081")
public class CartController {

    CartService cartService;

    @GetMapping("/cart/{item_id}")
    public CartItemDto getCartItem(@PathVariable Integer item_id) {
        return cartService.getCartItemByItemId(item_id);
    }

    @GetMapping("/cart/user/{customer_id}")
    public CartItemListDto getCartItems(@PathVariable Integer customer_id) {
        return cartService.getCartItems(customer_id);
    }

    @PostMapping("/cart")
    public IdDto createItem(@Validated @RequestBody CartItemDto cartItemDto) {
        return cartService.createCartItem(cartItemDto);
    }

    @PutMapping("/cart")
    public MessageDto updateItem(@Validated @RequestBody CartItemDto cartItemDto) {
        return cartService.updateCartItem(cartItemDto);
    }

    @DeleteMapping("/cart/user/{user_id}")
    public MessageDto deleteItem(@PathVariable Integer user_id) {
        return cartService.deleteCartItem(user_id);
    }
}
