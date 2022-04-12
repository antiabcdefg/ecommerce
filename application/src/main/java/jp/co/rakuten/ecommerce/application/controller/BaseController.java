package jp.co.rakuten.ecommerce.application.controller;

import jp.co.rakuten.ecommerce.common.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

public class BaseController {

    @Autowired
    private RestTemplate restTemplate;

    protected String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    protected Integer getUserId() {
        String email = getUserName();
        CustomerDto response = restTemplate.getForObject("/users/email/" + email, CustomerDto.class);
        return response.getId();
    }

    protected String getAuthority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().map(i -> i.getAuthority()).collect(Collectors.toList()).get(0);
    }

}
