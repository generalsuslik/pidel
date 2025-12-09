package com.pidel.kafka;

import com.pidel.dto.PaymentProcessedEvent;
import com.pidel.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener {

    private final OrderService orderService;

    @KafkaListener(topics = "payments", groupId = "order-service")
    public void processPayment(PaymentProcessedEvent event) {
        log.info("Received PaymentProcessedEvent {}", event);
        orderService.updateOrderPaymentStatus(
                event.getOrderId(),
                event.getStatus()
        );
    }
}
