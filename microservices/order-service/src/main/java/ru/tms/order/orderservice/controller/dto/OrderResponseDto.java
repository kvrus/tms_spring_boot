package ru.tms.order.orderservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tms.order.orderservice.model.OrderLineItems;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private String orderNumber;
    private List<OrderLineItemsDto> items;
}
