package com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.web.controllers;

import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.PagedResult;
import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.Product;
import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.ProductNotFoundException;
import com.cyngofokglobal.catalogservice.sivalabs.bookshop.catalog.domain.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Fetching Products for page: {}", pageNo);
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
//        sleep();
        log.info("Fetching Products for code: {}", code);
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }

    void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
