package com.cyngofokglobal.orderservice.domain;

import com.cyngofokglobal.orderservice.clients.catalog.ProductServiceClient;
import com.cyngofokglobal.orderservice.domain.models.CreateOrderRequest;
import com.cyngofokglobal.orderservice.domain.models.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductServiceClient client;

    OrderValidator(ProductServiceClient client) {
        this.client = client;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            Product product = client.getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOderException(("Invalid Product code:" + item.code())));
            if (item.price().compareTo(product.price()) != 0) {
                log.error("Product price not matching. Actual price:{}, received price:{}",
                        product.price(), item.price());
                throw new InvalidOderException("Product price not matching");
            }
        }
    }
}