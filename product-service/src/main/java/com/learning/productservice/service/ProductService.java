package com.learning.productservice.service;

import com.learning.productservice.dto.ProductRequest;
import com.learning.productservice.dto.ProductResponse;
import com.learning.productservice.model.Product;
import com.learning.productservice.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public ProductRequest createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .desc(productRequest.getDesc())
                .price(productRequest.getPrice())
                .build();


        productRepo.save(product);
        log.info("Product {} is saved", product.getId());
        return  productRequest;
    }
        public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
          }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .desc(product.getDesc())
                .price(product.getPrice())
                .build();
    }

}
