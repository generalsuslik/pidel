package com.pidel.service;

import com.pidel.dto.OrderCreatedEvent;
import com.pidel.entity.Order;
import com.pidel.entity.common.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    List<Order> findAllByUser(String username);
    Order createOrder(OrderCreatedEvent event);
    void updateOrderPaymentStatus(Long orderId, OrderStatus status);
}
