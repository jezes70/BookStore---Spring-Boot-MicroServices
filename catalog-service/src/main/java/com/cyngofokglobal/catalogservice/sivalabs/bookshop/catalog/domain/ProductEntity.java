package com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "product code is required")
    private String code;

    @NotEmpty(message = "product name is required")
    @Column(nullable = false)
    private String name;

    private String description;

    private String imageUrl;

    @NotEmpty(message = "product price is required")
    @DecimalMin("0.1")
    @Column(nullable = false)
    private BigDecimal price;
}
