package com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCode(String code);

    //     Optional<Product> findAllCode(String code);
}
