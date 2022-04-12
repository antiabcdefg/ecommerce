package jp.co.rakuten.ecommerce.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${ecommerce.api.rootUrl}")
    private String apiRootUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(apiRootUrl).build();
    }
}
