package com.pidel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long pizzaId;
    private Integer quantity;
    private Double price;
    private Integer pizzaSize;
}
