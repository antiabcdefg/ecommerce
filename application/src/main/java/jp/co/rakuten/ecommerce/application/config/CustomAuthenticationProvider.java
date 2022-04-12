package jp.co.rakuten.ecommerce.application.config;

import jp.co.rakuten.ecommerce.common.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(email.equalsIgnoreCase(env.getProperty("admin.name"))&&password.equalsIgnoreCase(env.getProperty("admin.password")))
            return new UsernamePasswordAuthenticationToken(email,password,Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        try {
            CustomerDto response = restTemplate.getForObject("/users/email/" + email, CustomerDto.class);

            if (!password.equals(response.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }

            if (password.equals(response.getPassword()))
                return new UsernamePasswordAuthenticationToken(email, password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        }catch (HttpClientErrorException.NotFound e){
            throw new BadCredentialsException("Username not found");
        }

        throw new BadCredentialsException("Error!!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}