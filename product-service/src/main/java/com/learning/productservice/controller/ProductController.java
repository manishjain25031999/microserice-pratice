package com.learning.productservice.controller;

import com.learning.productservice.dto.ProductRequest;
import com.learning.productservice.dto.ProductResponse;
import com.learning.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
   private  ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRequest createProduct(@RequestBody ProductRequest productRequest)
    {
           return productService.createProduct(productRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

}
