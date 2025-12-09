package com.pidel.service.impl;

import com.pidel.entity.OrderItem;
import com.pidel.repository.OrderItemRepository;
import com.pidel.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem findByOrderId(Long id) {
        return orderItemRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
