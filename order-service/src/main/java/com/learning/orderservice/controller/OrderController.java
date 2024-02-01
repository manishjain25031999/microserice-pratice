package com.learning.orderservice.controller;

import com.learning.orderservice.dto.OrderRequest;
import com.learning.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest)
    {
           orderService.placeOrder(orderRequest);
           return "order placed sucessfully";
    }
}
