package com.learning.orderservice.repo;

import com.learning.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
