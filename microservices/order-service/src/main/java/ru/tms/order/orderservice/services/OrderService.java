package ru.tms.order.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tms.order.orderservice.controller.dto.OrderDto;
import ru.tms.order.orderservice.controller.dto.OrderLineItemsDto;
import ru.tms.order.orderservice.controller.dto.OrderResponseDto;
import ru.tms.order.orderservice.model.Order;
import ru.tms.order.orderservice.model.OrderLineItems;
import ru.tms.order.orderservice.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    final private OrderRepository repository;

    public List<OrderResponseDto> getOrders() {
        return new ArrayList();
    }


    public void createOrders(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setItems(orderDto.getItems().stream().map(
                this::mapFromDto
        ).toList());
        repository.save(order);
    }

    private OrderLineItems mapFromDto(OrderLineItemsDto itemsDto) {
        OrderLineItems item = new OrderLineItems();
        item.setPrice(itemsDto.getPrice());
        item.setQuantity(itemsDto.getQuantity());
        item.setSkuCode(UUID.randomUUID().toString());
        return item;
    }
}
