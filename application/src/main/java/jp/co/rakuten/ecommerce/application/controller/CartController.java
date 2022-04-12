package jp.co.rakuten.ecommerce.application.controller;

import jp.co.rakuten.ecommerce.common.dto.CartItemDto;
import jp.co.rakuten.ecommerce.common.dto.CartItemListDto;
import jp.co.rakuten.ecommerce.common.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/cart")
    public ModelAndView list() {
        ModelAndView model = new ModelAndView("cart/list");

        try {
            CartItemListDto response = restTemplate.getForObject("/cart/user/" + getUserId(), CartItemListDto.class);

            List<CartItemDto> cartItemListDto = response.getCartItems();
            Map<Integer, Integer> itemQuantityMap = new HashMap<>();
            Double total = 0.0;
            for (CartItemDto c : cartItemListDto) {
                total += c.getPrice() * c.getQuantity();
                ItemDto item = restTemplate.getForObject("/item/" + c.getItemId(), ItemDto.class);
                itemQuantityMap.putIfAbsent(item.getItemId(), item.getInventoryCount());
            }

            model.addObject("items", cartItemListDto);
            model.addObject("temp_items", itemQuantityMap);
            model.addObject("total", total);

        } catch (HttpClientErrorException.NotFound e) {
            model.addObject("items", new ArrayList<CartItemDto>());
            model.addObject("temp_items", new HashMap<Integer, Integer>());
            model.addObject("total", 0);
        }

        return model;
    }

    @PostMapping("/cart/add")
    public String add(@ModelAttribute CartItemDto cartItemDto) {
        cartItemDto.setCustomerId(getUserId());
        ItemDto item = restTemplate.getForObject("/item/" + cartItemDto.getItemId(), ItemDto.class);

        try {
            CartItemDto response = restTemplate.getForObject("/cart/" + cartItemDto.getItemId(), CartItemDto.class);
            cartItemDto.setId(response.getId());
            Integer total = cartItemDto.getQuantity() + response.getQuantity();
            if (total > item.getInventoryCount()) cartItemDto.setQuantity(item.getInventoryCount());
            else cartItemDto.setQuantity(total);
            restTemplate.put("/cart", cartItemDto);
        } catch (HttpClientErrorException.NotFound e) {
            if (cartItemDto.getName() == null) {
                cartItemDto.setName(item.getName());
                cartItemDto.setPhotoAddress(item.getPhotoAddress());
                cartItemDto.setPrice(item.getPrice());
                cartItemDto.setDescription(item.getDescription());
                if (item.getInventoryCount() > 0) cartItemDto.setQuantity(1);
                else return "redirect:/item/list";
            }
            restTemplate.postForObject("/cart", cartItemDto, CartItemDto.class);
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/change")
    public String change(@ModelAttribute CartItemDto cartItemDto) {
        try {
            CartItemDto response = restTemplate.getForObject("/cart/" + cartItemDto.getItemId(), CartItemDto.class);

//            ItemDto item = restTemplate.getForObject("/item/" + cartItemDto.getItemId(), ItemDto.class);

//            if(item.getInventoryCount()<response.getQuantity()) return "redirect:/cart";

            cartItemDto.setId(response.getId());
            restTemplate.put("/cart", cartItemDto);
        } catch (HttpClientErrorException.NotFound e) {
            restTemplate.postForObject("/cart", cartItemDto, CartItemDto.class);
        }

        return "redirect:/cart";
    }


}
