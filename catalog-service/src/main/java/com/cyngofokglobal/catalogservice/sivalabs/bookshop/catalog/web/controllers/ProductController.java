package com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.web.controllers;

import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.PagedResult;
import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.Product;
import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.ProductNotFoundException;
import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
