package com.cyngofokglobal.order_service;

import com.cyngofokglobal.orderservice.OrderServiceApplication;
import org.springframework.boot.SpringApplication;

public class TestOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(OrderServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
