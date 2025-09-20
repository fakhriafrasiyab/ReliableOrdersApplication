package com.faxriafrasiyab.reliable.orders.service;

import com.faxriafrasiyab.reliable.orders.dto.OrderDto;
import com.faxriafrasiyab.reliable.orders.entity.Order;
import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import com.faxriafrasiyab.reliable.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerEmail(orderDto.customerEmail());
        order.setAmount(orderDto.amount());
        order.setCurrency(orderDto.currency());

        return orderRepository.save(order);
    }

    public List<Order> retrieveAllOrders() {
        return orderRepository.findAll();
    }

    public void handleNewOrderEvent(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("Processing business logic for new order" + orderCreatedEvent.orderId());
    }


}
