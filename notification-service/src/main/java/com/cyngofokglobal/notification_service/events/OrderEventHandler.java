package com.cyngofokglobal.notification_service.events;

import com.cyngofokglobal.notification_service.domain.NotificationService;
import com.cyngofokglobal.notification_service.domain.OrderEventEntity;
import com.cyngofokglobal.notification_service.domain.OrderEventRepository;
import com.cyngofokglobal.notification_service.domain.models.OrderCancelledEvent;
import com.cyngofokglobal.notification_service.domain.models.OrderCreatedEvent;
import com.cyngofokglobal.notification_service.domain.models.OrderDeliveredEvent;
import com.cyngofokglobal.notification_service.domain.models.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Transactional
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    public OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }
    @RabbitListener(queues = "{notification.new-orders-queue}")
    public void handle(OrderCreatedEvent event) {
       if (orderEventRepository.existsByEventId(event.eventId())) {
           log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.eventId());
           return;
       }
       log.info("Received a OrderCreatedEvent with orderNumber:{}: ", event.OrderNumber());
       notificationService.sendOrderCreatedNotification(event);
       var orderEvent = new OrderEventEntity(event.eventId());
       orderEventRepository.save(orderEvent);
    }
    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    public void handle(OrderDeliveredEvent event) {
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderDeliveredEvent with eventId: {}", event.eventId());
            return;
        }
        log.info("Received a OrderDeliveredEvent with orderNumber:{}: ", event.orderNumber());
        notificationService.sendOrderDeliveredNotification(event);
        var orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }
    @RabbitListener(queues = "${notification.cancelled-orders-queue}")
    public void handle(OrderCancelledEvent event) {
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received a OrderCancelledEvent with orderNumber:{}: ", event.eventId());
            return;
        }
        notificationService.sendOrderCancelledNotification(event);
        log.info("Received duplicate OrderErrorEvent with eventId: {}", event.OrderNumber());
        var orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);
    }
    @RabbitListener(queues = "${notification.error-orders-queue}")
    public void handle(OrderErrorEvent event) {
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderErrorEvent with eventId: {}", event.eventId());
            return;
        }
        log.info("Received a OrderErrorEvent with orderNumber:{}: ", event.orderNumber());
        notificationService.sendOrderErrorEventNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEvent);

    }
}
