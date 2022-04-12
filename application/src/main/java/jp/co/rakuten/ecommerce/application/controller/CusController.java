package jp.co.rakuten.ecommerce.application.controller;

import jp.co.rakuten.ecommerce.common.dto.CustomerDto;
import jp.co.rakuten.ecommerce.common.dto.OrderDetailDto;
import jp.co.rakuten.ecommerce.common.dto.OrderDto;
import jp.co.rakuten.ecommerce.common.dto.OrderListDto;
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
import java.util.Map;

@Controller
public class CusController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user/login")
    public ModelAndView login(@ModelAttribute CustomerDto customerDto) {

        ModelAndView model = new ModelAndView("user/login");
        model.addObject("user", customerDto);

        return model;
    }

    @GetMapping("/user/register")
    public ModelAndView registerPage(@ModelAttribute CustomerDto customerDto) {

        ModelAndView model = new ModelAndView("user/register");
        model.addObject("user", customerDto);

        return model;
    }

    @PostMapping("/user/toRegister")
    public String register(@ModelAttribute CustomerDto customerDto) {

        try {
            CustomerDto temp = restTemplate.getForObject("/users/email/" + customerDto.getEmail(), CustomerDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            customerDto.setEnabled(true);

            restTemplate.postForObject("/users", customerDto, CustomerDto.class);
        }

        return "redirect:/user/login";
    }

    @GetMapping("/user/profile")
    public ModelAndView profile() {
        ModelAndView model = new ModelAndView("user/profile");

        CustomerDto response = restTemplate.getForObject("/users/email/" + getUserName(), CustomerDto.class);
        model.addObject("customer", response);
        try {
            OrderListDto response2 = restTemplate.getForObject("/orders/user/" + getUserId(), OrderListDto.class);
            model.addObject("orders", response2.getOrders());

            Map<Integer, Double> totalMap = new HashMap<>();
            for (OrderDto o : response2.getOrders()) {
                Double total = 0.0;
                for (OrderDetailDto d : o.getItems()) total += d.getPrice() * d.getQuantity();
                totalMap.putIfAbsent(o.getId(), total);
            }

            model.addObject("totals", totalMap);
        } catch (HttpClientErrorException.NotFound e) {
            model.addObject("orders", new ArrayList<OrderDto>());
            model.addObject("totals", new HashMap<Integer, Double>());
        }
        return model;
    }
}
