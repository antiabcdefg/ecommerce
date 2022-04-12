package jp.co.rakuten.ecommerce.application.controller;

import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.ItemDto;
import jp.co.rakuten.ecommerce.common.dto.ItemListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/admin/item")
public class AdminItemController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/list")
    public ModelAndView list() {

        ItemListDto response = restTemplate.getForObject("/items", ItemListDto.class);

        ModelAndView model = new ModelAndView("admin/item/list");

        model.addObject("items", response.getItems());

        return model;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable Integer id) {

        ItemDto item = restTemplate.getForObject("/item/" + id, ItemDto.class);

        ModelAndView model = new ModelAndView("admin/item/detail");

        model.addObject("item", item);

        return model;
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute ItemDto item) {
        Integer itemId = item.getItemId();
        if (itemId != null) {
            restTemplate.put("/items", item);
        }

        return "redirect:/admin/item/list";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute ItemDto item) {
        Integer itemId = item.getItemId();
        if (itemId != null) {
            restTemplate.put("/items", item);
        } else {
            IdDto idDto = restTemplate.postForObject("/items", item, IdDto.class);
            itemId = idDto.getId();
            return "redirect:/admin/item/list";
        }

        return "redirect:" + MvcUriComponentsBuilder.fromMethodName(AdminItemController.class, "refresh", itemId).build().toUri();
    }

    @GetMapping("/new")
    private ModelAndView add() {
        ItemDto item = new ItemDto();
        ModelAndView model = new ModelAndView("admin/item/newitem");
        model.addObject("item", item);
        return model;
    }

    @GetMapping("/detail/{id}/refresh")
    private ModelAndView refresh(@PathVariable Integer id) {
        ModelAndView model = detail(id);
        model.addObject("message", "Successfully Saved!");
        return model;
    }
}
