package com.cyngofokglobal.order_service.web.controller;

import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.ProductService;
import com.cyngofokglobal.order_service.AbstractIT;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import java.math.BigDecimal;
import com.cyngofokglobal.orderservice.clients.catalog.Product;
import io.restassured.common.mapper.TypeRef;
import com.cyngofokglobal.order_service.testdata.TestDataFactory;
import com.cyngofokglobal.orderservice.domain.models.OrderSummary;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import static org.hamcrest.CoreMatchers.is;
import java.math.BigDecimal;
import java.util.List;

@Sql("/test-orders.sql")
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTests extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private void mockGetProductByCode(String code, String name, BigDecimal price) {
        Product mockProduct = new Product(code, name, price);
        Mockito.when(productService.getProductByCode(code)).thenReturn(mockProduct);
    }

    private String getToken() {
        return "mocked-jwt-token";

        @Nested
        class CreateOrderTests {
            @Test
            void shouldCreateOrderSuccessfully() {
                mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
                var payload =
                        """
                                       {
                                          "customer" : {
                                             "name": "Cyngofokglobal",
                                             "email": cyngofokglobal@gmail.com",
                                             "phone": "12345678910"
                                          },
                                          "deliveryAddress": {
                                              "addressLine1": "Amadi 12",
                                              "addressLine2": "Moore House",
                                              "city": "Port Harcourt",
                                              "state": "Rivers",
                                              "zipCode": "500001",
                                              "country": "Nigeria"
                                          },
                                          "items": [
                                            {
                                                "code": "P100",
                                                "name": "Product 1",
                                                "price": "25.50",
                                                "quantity": 1
                                                
                                            }
                                          ]
                                       }
                                """;
                given().contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + getToken())
                        .body(payload)
                        .with()
                        .post("/api/orders")
                        .then()
                        .statusCode(HttpStatus.CREATED.value())
                        .body("orderNumber", notNullValue());
            }

            @Test
            void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
                var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
                given().contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + getToken())
                        .body(payload)
                        .when()
                        .post("/api/orders")
                        .then()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
            }
        }

        @Nested
        class GetOrdersTests {
            @Test
            void shouldGetOrdersSuccessfully() {
                List<OrderSummary> orderSummaries = given().when()
                        .header("Authorization", "Bearer " + getToken())
                        .get("/api/orders")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(new TypeRef<>() {
                        });

                assertThat(orderSummaries).hasSize(2);
            }
        }

        @Nested
        class GetOrderByOrderNumberTests {
            String orderNumber = "order-123";

            @Test
            void shouldGetOrderSuccessfully() {
                given().when()
                        .header("Authorization", "Bearer " + getToken())
                        .get("/api/orders/{orderNumber}", orderNumber)
                        .then()
                        .statusCode(200)
                        .body("orderNumber", is(orderNumber))
                        .body("items.size()", is(2));
            }
        }
    }

}
