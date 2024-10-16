package com.cyngofokglobal.bookshop_webapp.clients;

import com.cyngofokglobal.bookshop_webapp.ApplicationProperties;
import com.cyngofokglobal.bookshop_webapp.clients.catalog.CatalogServiceClient;
import com.cyngofokglobal.bookshop_webapp.clients.orders.OrderServiceClient;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
class ClientsConfig {
    private final ApplicationProperties properties;

    ClientsConfig(ApplicationProperties properties) {
        this.properties = properties;
    }
    @Bean
    RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> restClientBuilder
                .baseUrl(properties.apiGatewayUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(6))
                        .withReadTimeout(Duration.ofSeconds(6))));
    }
    @Bean
    CatalogServiceClient catalogServiceClient(RestClient.Builder builder) {
       RestClient restClient = builder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(CatalogServiceClient.class);
    }
    @Bean
    OrderServiceClient orderServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(OrderServiceClient.class);
    }
}



