package com.pidel.service;

import com.pidel.entity.Order;
import com.pidel.entity.common.OrderStatus;

public interface OrderService {
    Order createOrder(Order order);
    void updateOrderPaymentStatus(Long orderId, OrderStatus status);
}
