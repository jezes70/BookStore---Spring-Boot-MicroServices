package com.cyngofokglobal.orderservice.domain;

import com.cyngofokglobal.orderservice.domain.models.OrderStatus;
import com.cyngofokglobal.orderservice.domain.models.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus status);
    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updatedOrderStatus(String orderNumber, OrderStatus status) {
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        this.save(order);
    }

    @Query(
             """
           select  new com.cyngofokglobal.orderservice.domain.models.OrderStatus(o.orderNumber, o.status)
                   
           from OrderEntity o
           where o.userName = :userName
          """)
    List<OrderSummary> findByUserName(String userName);

    @Query(
            """
         select distinct o from OrderEntity o left join fetch o.items
         where o.userName = :userName and o.orderNumber = :orderNumber
         """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
