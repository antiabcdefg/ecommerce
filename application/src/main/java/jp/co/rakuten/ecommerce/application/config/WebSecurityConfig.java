package jp.co.rakuten.ecommerce.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/favicon.ico", "/css/**", "/images/**", "/item/list", "/item/se/**", "/order/hot","/user/login", "/user/register", "/user/toRegister").permitAll()
                .antMatchers("/user/**", "/order/**", "/cart/**", "/item/review/**").hasAuthority("ROLE_USER")
//                .antMatchers("/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/user/login")
                .successHandler(new MyUrlAuthenticationSuccessHandler())
//                .loginProcessingUrl("/user/toLogin")
//                .defaultSuccessUrl("/item/list")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/item/list")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

}

