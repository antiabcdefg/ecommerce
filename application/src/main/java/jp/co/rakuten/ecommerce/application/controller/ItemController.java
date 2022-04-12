package jp.co.rakuten.ecommerce.application.controller;

import jp.co.rakuten.ecommerce.common.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ItemController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String index() {
        return "redirect:" + MvcUriComponentsBuilder.fromMethodName(ItemController.class, "list");
    }

    @GetMapping("/item/list")
    public ModelAndView list() {

        ItemListDto itemListDto = restTemplate.getForObject("/items", ItemListDto.class);

        ModelAndView model = new ModelAndView("item/list");

        model.addObject("items", itemListDto.getItems());

        try {
            OrderDetailListDto response = restTemplate.getForObject("/orders/all", OrderDetailListDto.class);

            model.addObject("hot", getHots(response));

        } catch (HttpClientErrorException.NotFound e) {
            model.addObject("hot", new ArrayList<OrderDetailDto>());
        }

        return model;
    }

    @GetMapping("/item/se/{name}")
    public ModelAndView search(@PathVariable String name) {

        ItemListDto response = restTemplate.getForObject("/items/" + name, ItemListDto.class);

        ModelAndView model = new ModelAndView("item/list");

        model.addObject("items", response.getItems());

        return model;
    }

    @GetMapping("/item/detail/{itemId}")
    public ModelAndView detail(@PathVariable Integer itemId) {
        ModelAndView model = new ModelAndView("item/detail");
        try {
            ItemDto item = restTemplate.getForObject("/item/" + itemId, ItemDto.class);

            model.addObject("item", item);

            ReviewListDto reviewListDto = restTemplate.getForObject("/reviews", ReviewListDto.class);

            model.addObject("reviews", reviewListDto.getReviewDtos());

            ItemListDto related = null;

            if (item.getName().length() > 3)
                related = restTemplate.getForObject("/items/" + item.getName().substring(0, 4), ItemListDto.class);
            else related = restTemplate.getForObject("/items/" + item.getName(), ItemListDto.class);

            model.addObject("related", related.getItems());

            OrderDetailListDto response = restTemplate.getForObject("/orders/all", OrderDetailListDto.class);

            model.addObject("hot", getHots(response));
        } catch (HttpClientErrorException.NotFound e) {
            model.addObject("item", new ItemDto());
            model.addObject("reviews", new ArrayList<ReviewDto>());
            model.addObject("related", new ArrayList<ItemDto>());
            model.addObject("hot", new ArrayList<OrderDetailDto>());
        }

        return model;
    }


    @GetMapping("/item/review/{orderId}/{itemId}")
    public ModelAndView review(@PathVariable Integer orderId, @PathVariable Integer itemId) {
        ModelAndView model = new ModelAndView("review/review");

        ItemDto item = restTemplate.getForObject("/item/" + itemId, ItemDto.class);
        model.addObject("item", item);
        model.addObject("orderId", orderId);

        model.addObject("review", new ReviewDto());

        return model;
    }

    @PostMapping("/item/review/refresh")
    private ModelAndView complete(@ModelAttribute ReviewDto reviewDto) {
        reviewDto.setCustomerId(getUserId());
        CustomerDto customerDto = restTemplate.getForObject("/users/email/" + getUserName(), CustomerDto.class);
        reviewDto.setCusName(customerDto.getLastName() + " " + customerDto.getFirstName());
        restTemplate.postForObject("/reviews", reviewDto, ReviewDto.class);

        return detail(reviewDto.getItemId());
    }

    private List<OrderDetailDto> getHots(OrderDetailListDto response) {
        Map<Integer, Integer> hots = new HashMap<>();
        Map<Integer, OrderDetailDto> itemInfos = new HashMap<>();
        for (OrderDetailDto o : response.getOrderDetailDtos()) {
            hots.put(o.getItemId(), hots.getOrDefault(o.getItemId(), 0) + o.getQuantity());
            itemInfos.put(o.getItemId(), o);
        }

        List<Integer> itemIds = hots.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder())).map(Map.Entry::getKey).limit(10).collect(Collectors.toList());
        List<OrderDetailDto> items = new ArrayList<>();
        for (Integer i : itemIds) {
            items.add(itemInfos.get(i));
        }
        return items;
    }

}
