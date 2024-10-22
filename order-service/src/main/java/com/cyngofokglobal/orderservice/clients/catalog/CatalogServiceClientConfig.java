package com.cyngofokglobal.orderservice.clients.catalog;

import com.cyngofokglobal.orderservice.ApplicationProperties;
import lombok.Builder;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;



import java.time.Duration;

@Configuration
@Builder
class CatalogServiceClientConfig {
    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties properties) {
        return builder.baseUrl(properties.catalogServiceUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(6))
                        .withReadTimeout(Duration.ofSeconds(6))))
                .build();
    }
}
