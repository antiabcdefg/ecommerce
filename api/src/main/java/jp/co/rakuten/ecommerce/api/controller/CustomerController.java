package jp.co.rakuten.ecommerce.api.controller;

import io.swagger.annotations.Api;
import jp.co.rakuten.ecommerce.api.service.CustomerService;
import jp.co.rakuten.ecommerce.common.dto.CustomerDto;
import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.MessageDto;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags="CustomerController")
@CrossOrigin(value = "http://localhost:18081")
@RestController
@AllArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @GetMapping("/users/{id}")
    public CustomerDto getCustomer(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/users/email/{email}")
    public CustomerDto getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping("/users")
    public IdDto createCus(@Validated @RequestBody CustomerDto customerDto) {
        return customerService.createCus(customerDto);
    }

    @PutMapping("/users")
    public MessageDto updateCus(@Validated @RequestBody CustomerDto customerDto) {
        return customerService.updateCus(customerDto);
    }
}
