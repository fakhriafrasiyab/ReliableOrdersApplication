package com.faxriafrasiyab.reliable.orders.service;

import com.faxriafrasiyab.reliable.orders.dto.OrderDto;
import com.faxriafrasiyab.reliable.orders.entity.Order;
import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import com.faxriafrasiyab.reliable.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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

        if (orderCreatedEvent.amount().doubleValue() <= 0) {
            throw new IllegalArgumentException("Amount must be positive for the order "
                    + orderCreatedEvent.orderId());
        }

        Optional<Order> orderOptional = orderRepository.findById(orderCreatedEvent.orderId());
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("PROCESSING");
            orderRepository.save(order);

            System.out.println("Order " + orderCreatedEvent.orderId() + " moved to " + order.getStatus());
        } else {
            System.err.println("Order not found for event " + orderCreatedEvent);
        }
    }


}
