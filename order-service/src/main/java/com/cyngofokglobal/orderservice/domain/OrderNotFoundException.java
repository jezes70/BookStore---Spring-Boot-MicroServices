package com.cyngofokglobal.orderservice.domain;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException forOrderNumber(String orderNumber) {
        return new OrderNotFoundException("order with Number" + orderNumber + "not found");
    }
}
