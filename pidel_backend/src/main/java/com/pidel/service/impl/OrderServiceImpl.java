package com.pidel.service.impl;

import com.pidel.entity.Order;
import com.pidel.entity.common.OrderStatus;
import com.pidel.kafka.OrderEventProducer;
import com.pidel.repository.OrderRepository;
import com.pidel.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    @Override
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        orderEventProducer.sendOrderCreatedEvent(savedOrder);

        return savedOrder;
    }

    @Override
    public void updateOrderPaymentStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        order.setStatus(status);
        orderRepository.save(order);
    }
}
