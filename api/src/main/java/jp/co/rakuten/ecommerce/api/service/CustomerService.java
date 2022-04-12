package jp.co.rakuten.ecommerce.api.service;

import jp.co.rakuten.ecommerce.api.entity.Customer;
import jp.co.rakuten.ecommerce.api.exception.NotFoundException;
import jp.co.rakuten.ecommerce.api.repository.CustomerRepository;
import jp.co.rakuten.ecommerce.common.dto.CustomerDto;
import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerDto getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new NotFoundException("Customer not found for id=" + id);
        }

        return getCusDto(customer.get());
    }

    public CustomerDto getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);

        if (customer.isEmpty()) {
            throw new NotFoundException("Customer not found for id=" + email);
        }

        return getCusDto(customer.get());
    }

    @Transactional
    public IdDto createCus(CustomerDto customerDto) {
        Customer customer = getCus(customerDto);
        customer = customerRepository.save(customer);
        return IdDto.builder().id(customer.getId()).build();
    }

    @Transactional
    public MessageDto updateCus(CustomerDto customerDto) {
        Customer customer = getCus(customerDto);
        customerRepository.save(customer);
        return MessageDto.builder().message("Success").build();
    }

    private CustomerDto getCusDto(Customer customer) {
        return CustomerDto.builder().id(customer.getId())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .address(customer.getAddress())
                .zipCode(customer.getZipCode())
                .phone(customer.getPhone())
                .enabled(customer.isEnabled())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    private Customer getCus(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(customerDto.getPassword());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setAddress(customerDto.getAddress());
        customer.setZipCode(customerDto.getZipCode());
        customer.setPhone(customerDto.getPhone());
        customer.setEnabled(customerDto.isEnabled());
        customer.setCreatedAt(new Date());
        return customer;
    }
}
