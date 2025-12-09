package com.pidel.service;

import com.pidel.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAll();
    OrderItem findByOrderId(Long orderId);
}
