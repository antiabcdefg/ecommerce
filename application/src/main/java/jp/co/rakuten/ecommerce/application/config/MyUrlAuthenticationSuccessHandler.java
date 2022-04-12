package jp.co.rakuten.ecommerce.application.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class MyUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = "/item/list";
        String current_user = getAuthority(authentication);
        switch (current_user) {
            case "ROLE_ADMIN":
                targetUrl = "/admin/item/list";
                break;
            case "ROLE_USER":
                targetUrl = "/item/list";
                break;
            default:
                targetUrl = "/item/list";

        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String getAuthority(Authentication authentication) {
        return authentication.getAuthorities().stream().map(i -> i.getAuthority()).collect(Collectors.toList()).get(0);
    }
}
