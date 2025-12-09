package com.pidel.controller;

import com.pidel.dto.OrderCreatedEvent;
import com.pidel.entity.Order;
import com.pidel.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/all/{username}")
    public List<Order> getAllOrdersByUser(@PathVariable String username) {
        return orderService.findAllByUser(username);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderCreatedEvent order) {
        return orderService.createOrder(order);
    }
}
