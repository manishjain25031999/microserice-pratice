package com.learning.orderservice.service;

import com.learning.orderservice.dto.InventoryResponse;
import com.learning.orderservice.dto.OrderLineItemDto;
import com.learning.orderservice.dto.OrderRequest;
import com.learning.orderservice.model.Order;
import com.learning.orderservice.model.OrderLineItems;
import com.learning.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

      @Autowired
      private OrderRepo orderRepo ;

      @Autowired
      private WebClient.Builder webClientBuilder ;

    public String placeOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes =  order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();



        // Inventory Service to check the stock is available or not .
        // http://localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone13-red

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder ->uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductResponse =  Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse ::isInStock);

        if(allProductResponse)
        {
            orderRepo.save(order);
            return "Order Placed Sucessfully";
        }
        else
        {
            throw new IllegalArgumentException("items is out of stock please try again later ");

        }
    }

    private OrderLineItems mapToDto(OrderLineItemDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}
