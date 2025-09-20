package com.faxriafrasiyab.reliable.orders.controller;

import com.faxriafrasiyab.reliable.orders.config.AppTopics;
import com.faxriafrasiyab.reliable.orders.dto.OrderDto;
import com.faxriafrasiyab.reliable.orders.entity.Order;
import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import com.faxriafrasiyab.reliable.orders.producer.OrderProducer;
import com.faxriafrasiyab.reliable.orders.repository.OrderRepository;
import com.faxriafrasiyab.reliable.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderProducer orderProducer;


    public OrderController(OrderService orderService, OrderProducer orderProducer) {
        this.orderService = orderService;
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto orderDto) {
        Order savedOrder = orderService.createOrder(orderDto);

        OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), savedOrder.getCustomerEmail(),
                savedOrder.getAmount(), savedOrder.getCurrency());
        orderProducer.sendOrderCreatedEvent(event);

        return savedOrder;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.retrieveAllOrders();
    }
}
