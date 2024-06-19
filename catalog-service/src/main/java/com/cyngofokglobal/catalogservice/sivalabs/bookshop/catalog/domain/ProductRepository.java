package com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCode(String code);

//     Optional<Product> findAllCode(String code);
}
