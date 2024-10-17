package com.cyngofokglobal.order_service.testdata;

import com.cyngofokglobal.orderservice.domain.Customer;
import com.cyngofokglobal.orderservice.domain.models.Address;
import com.cyngofokglobal.orderservice.domain.models.CreateOrderRequest;
import com.cyngofokglobal.orderservice.domain.models.OrderItem;
import org.instancio.Instancio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.instancio.Select.field;

public class TestDataFactory {
    static final List<String> VALID_COUNTIES = List.of("Nigeria","India", "Germany");
    static final Set<OrderItem> VALID_ORDER_ITEMS =
            Set.of(new OrderItem("P100", "Product 1", new BigDecimal("25.50"), 1));
    static final Set<OrderItem> INVALID_ORDER_ITEMS =
            Set.of(new OrderItem("ABCD", "Product 1", new BigDecimal("25.50"), 1));

    public static CreateOrderRequest createValidOrderRequest() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer.class, "email"), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
                .set(field(CreateOrderRequest.class, "items"), VALID_ORDER_ITEMS)
                .generate(field(Address.class, "country"), gen -> gen.oneOf(VALID_COUNTIES))
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithInvalidCustomer() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer.class,"email"), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Customer.class, "phone"), "")
                .generate(field(Address.class, "country"), gen -> gen.oneOf(VALID_COUNTIES))
                .set(field(CreateOrderRequest.class, "items"), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithInvalidDeliveryAddress() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer.class, "email"), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .set(field(Address.class, "country"), "")
                .set(field(CreateOrderRequest.class, "items"), VALID_ORDER_ITEMS)
                .create();
    }

    public static CreateOrderRequest createOrderRequestWithNoItems() {
        return Instancio.of(CreateOrderRequest.class)
                .generate(field(Customer.class, "email"), gen -> gen.text().pattern("#c#c#c#c#d#d@mail.com"))
                .generate(field(Address.class, "country"), gen -> gen.oneOf(VALID_COUNTIES))
                .set(field(CreateOrderRequest.class, "items"), Set.of())
                .create();
    }
}
