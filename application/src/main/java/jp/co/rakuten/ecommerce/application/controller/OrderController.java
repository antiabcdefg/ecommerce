package jp.co.rakuten.ecommerce.application.controller;


import jp.co.rakuten.ecommerce.common.dto.CartItemListDto;
import jp.co.rakuten.ecommerce.common.dto.ItemDto;
import jp.co.rakuten.ecommerce.common.dto.OrderDetailDto;
import jp.co.rakuten.ecommerce.common.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/add")
    public String add() {
        try {
            CartItemListDto cartItemListDto = restTemplate.getForObject("/cart/user/" + getUserId(), CartItemListDto.class);
            OrderDto orderDto = restTemplate.postForObject("/orders/cart", cartItemListDto.getCartItems(), OrderDto.class);
            return "redirect:" + MvcUriComponentsBuilder.fromMethodName(OrderController.class, "confirm", orderDto.getId()).build().toUri();
        } catch (HttpClientErrorException.NotFound e) {

        }

        return "redirect:/item/list";

    }

    @GetMapping("/confirm/{order_id}")
    public ModelAndView confirm(@PathVariable Integer order_id) {
        ModelAndView model = new ModelAndView("order/confirm");

        try {
            OrderDto response = restTemplate.getForObject("/orders/" + order_id, OrderDto.class);

            Double total = 0.0;
            for (OrderDetailDto o : response.getItems()) total += o.getPrice() * o.getQuantity();

            model.addObject("order", response);
            model.addObject("total", total);

        } catch (HttpClientErrorException.NotFound e) {
            model.addObject("order", new OrderDto());
            model.addObject("total", 0);
        }

        return model;
    }

    @PostMapping("/complete")
    public ModelAndView complete(@ModelAttribute OrderDto orderDto) {

        ModelAndView model = new ModelAndView("order/completed");

        try {
            OrderDto response = restTemplate.getForObject("/orders/" + orderDto.getId(), OrderDto.class);

            response.setStatus("COMPLETED");
            restTemplate.put("/orders", response);
            restTemplate.delete("/cart/user/" + getUserId());
            for (OrderDetailDto orderDetailDto : response.getItems()) {
                ItemDto item = restTemplate.getForObject("/item/" + orderDetailDto.getItemId(), ItemDto.class);
                item.setInventoryCount(item.getInventoryCount() - orderDetailDto.getQuantity());
                restTemplate.put("/items", item);
            }

            model.addObject("order", response);
        } catch (HttpClientErrorException.NotFound e) {
            model.addObject("order", new OrderDto());
        }
        return model;
    }
}
