package jp.co.rakuten.ecommerce.application.controller;

import jp.co.rakuten.ecommerce.common.dto.OrderDto;
import jp.co.rakuten.ecommerce.common.dto.OrderListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController extends BaseController{

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/list")
    public ModelAndView list() {

        OrderListDto response = restTemplate.getForObject("/orders", OrderListDto.class);

        ModelAndView model = new ModelAndView("admin/order/list");

        model.addObject("orders", response.getOrders());

        return model;
    }

    @PostMapping("/complete")
    public ModelAndView complete(@ModelAttribute OrderDto orderDto) {

        try {
            OrderDto response = restTemplate.getForObject("/orders/" + orderDto.getId(), OrderDto.class);

            response.setStatus("COMPLETED");
            restTemplate.put("/orders", response);

        } catch (HttpClientErrorException.NotFound e) {

        }
        return list();
    }


}
