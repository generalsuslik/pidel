package com.pidel.dto;

import com.pidel.entity.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {
    private Long orderId;
    private OrderStatus status;
    private String transactionId;
    private String timestamp;
}
