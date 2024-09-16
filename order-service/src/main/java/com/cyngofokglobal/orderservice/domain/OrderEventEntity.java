package com.cyngofokglobal.orderservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "order_evemts")
public class OrderEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_event_id_generator")
    @SequenceGenerator(name = "order_event_id_generator", sequenceName = "order_event_id_seq")
    public Long id;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Enumerated(EnumType.STRING)
    private OrderEventType eventType;

    @Column(nullable = false)
    private String payload;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "upated_at")
    private LocalDateTime updatedAt;
}
