package com.faxriafrasiyab.reliable.orders.controller;

import com.faxriafrasiyab.reliable.orders.config.AppTopics;
import com.faxriafrasiyab.reliable.orders.dto.OrderDto;
import com.faxriafrasiyab.reliable.orders.entity.Order;
import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import com.faxriafrasiyab.reliable.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    //    @Autowired
    private final KafkaTemplate<String, Object> kafka;
    private final AppTopics ordersTopic;

    public OrderController(OrderRepository orderRepository, KafkaTemplate<String, Object> kafka,
                           AppTopics ordersTopic) {
        this.orderRepository = orderRepository;
        this.kafka = kafka;
        this.ordersTopic = ordersTopic;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerEmail(orderDto.customerEmail());
        order.setAmount(orderDto.amount());
        order.setCurrency(orderDto.currency());
        Order orderSaved = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getCustomerEmail(),
                order.getAmount(), order.getCurrency());

        kafka.send(ordersTopic.orders(), String.valueOf(orderSaved.getId()), event)
                .whenComplete((metadata, exception) -> {
                    if (exception != null) {
                        System.err.println("Error sending order to topic " + ordersTopic +
                                "Error message: " + exception.getMessage());
                    } else {
                        System.out.println("Event sent to kafka:" + metadata.getRecordMetadata().topic());
                    }
                });
        return orderSaved;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
