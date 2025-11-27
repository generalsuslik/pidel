package com.pidel.kafka;

import com.pidel.dto.OrderCreatedEvent;
import com.pidel.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreatedEvent(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getUserId(),
                order.getTotal()
        );
        kafkaTemplate.send("orders", event);
    }
}
