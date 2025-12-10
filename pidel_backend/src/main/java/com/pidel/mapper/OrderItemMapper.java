package com.pidel.mapper;

import com.pidel.dto.OrderItemDto;
import com.pidel.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);
    List<OrderItemDto> toDtoList(List<OrderItem> orderItems);
    OrderItem toEntity(OrderItemDto dto);
    List<OrderItem> toEntityList(List<OrderItemDto> dtos);
}
