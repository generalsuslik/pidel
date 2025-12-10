package com.pidel.service.impl;

import com.pidel.dto.OrderCreatedEvent;
import com.pidel.dto.OrderItemDto;
import com.pidel.entity.Order;
import com.pidel.entity.OrderItem;
import com.pidel.entity.common.OrderStatus;
import com.pidel.kafka.OrderEventProducer;
import com.pidel.mapper.OrderItemMapper;
import com.pidel.repository.OrderRepository;
import com.pidel.service.OrderService;
import com.pidel.service.PizzaService;
import com.pidel.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderEventProducer orderEventProducer;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final PizzaService pizzaService;
    private final UserService userService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByUser(String username) {
        return findAll().stream()
                .filter(order -> Objects.equals(order.getUsername(), username))
                .toList();
    }

    @Override
    public Order createOrder(OrderCreatedEvent event) {
        log.info("Creating order {}", event);

        Order order = Order.builder()
                .username(event.getUsername())
                .address(extractAddress(event))
                .items(orderItemMapper.toEntityList(event.getItems()))
                .status(OrderStatus.CREATED)
                .totalPrice(event.getTotalPrice())
                .build();

        List<OrderItem> items = orderItemMapper.toEntityList(checkAndGetOrderItems(event));
        for (int i = 0; i < items.size(); ++i) {
            items.get(i).setOrder(order);
            items.get(i).setPizza(pizzaService.findById(event.getItems().get(i).getPizzaId()));
        }
        order.setItems(items);

        log.info("Saving new order {}", order);
        Order savedOrder = orderRepository.save(order);

        event.setOrderId(savedOrder.getId());
        log.info("Sending order created event {}", event);
        orderEventProducer.sendOrderCreatedEvent(event);

        return savedOrder;
    }

    @Override
    public void updateOrderPaymentStatus(Long orderId, OrderStatus status) {
        log.info("Updating order payment status for order {}, status {}", orderId, status);
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        order.setStatus(status);
        log.info("Saving updated order with new status {}", order);
        orderRepository.save(order);
    }

    private String extractAddress(OrderCreatedEvent event) {
        if (event.getAddress() != null) {
            return event.getAddress();
        }
        return userService.getAddress(event.getUsername());
    }

    private List<OrderItemDto> checkAndGetOrderItems(@NonNull OrderCreatedEvent event) {
        List<OrderItemDto> items = event.getItems();
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Order has no items");
        }
        return items;
    }
}
