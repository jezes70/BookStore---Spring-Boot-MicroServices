package com.cyngofokglobal.orderservice.domain.models;

import com.rabbitmq.client.Address;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderErrorEvent(
        String eventId,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        String reason,
        LocalDateTime createdAt) { }
