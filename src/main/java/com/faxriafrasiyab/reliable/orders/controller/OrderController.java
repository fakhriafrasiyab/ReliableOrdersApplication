package com.faxriafrasiyab.reliable.orders.controller;

import com.faxriafrasiyab.reliable.orders.dto.OrderDto;
import com.faxriafrasiyab.reliable.orders.entity.Order;
import com.faxriafrasiyab.reliable.orders.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerEmail(orderDto.customerEmail());
        order.setAmount(orderDto.amount());
        order.setCurrency(orderDto.currency());
        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
