package com.pidel.service;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.pidel.entity.Order;
import com.pidel.entity.common.OrderStatus;
import com.pidel.repository.OrderRepository;
import com.pidel.service.impl.OrderServiceImpl;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllReturnsEmptyList() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(orderService.findAll().isEmpty());
    }

    @Test
    void testUpdateOrderPaymentStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CREATED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        orderService.updateOrderPaymentStatus(1L, OrderStatus.PAID);
        assertEquals(OrderStatus.PAID, order.getStatus());
        verify(orderRepository).save(order);
    }
}
