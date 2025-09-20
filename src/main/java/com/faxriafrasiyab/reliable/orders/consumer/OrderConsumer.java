package com.faxriafrasiyab.reliable.orders.consumer;

import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import com.faxriafrasiyab.reliable.orders.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {


    private final OrderService orderService;

    public OrderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${app.topics.orders}", groupId = "reliable-orders")
    public void consume(OrderCreatedEvent orderConsumeEvent) {
        System.out.println("Order Consume Event: " + orderConsumeEvent);

        if (orderConsumeEvent.amount().doubleValue() <= 0) {
            System.err.println("Invalid Order Amount: " + orderConsumeEvent.amount()
                    + " Order Event:" + orderConsumeEvent);
            return;
        }
        orderService.handleNewOrderEvent(orderConsumeEvent);

    }

}
