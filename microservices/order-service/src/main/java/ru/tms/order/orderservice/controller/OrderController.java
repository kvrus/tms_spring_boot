package ru.tms.order.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tms.order.orderservice.controller.dto.OrderDto;
import ru.tms.order.orderservice.controller.dto.OrderResponseDto;
import ru.tms.order.orderservice.services.OrderService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    final private OrderService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDto> getOrders() {
        return service.getOrders();
    }

    // curl -d '{"items":[{"skuCode":"iPhone_X","price": 1200.23,"quantity":1}, {"skuCode":"iPad","price": 1400.23,"quantity":1}]}' -H "Content-Type: application/json" -X POST http://localhost:8081/api/order


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderDto order) {
        service.createOrders(order);
        return "";
    }
}
