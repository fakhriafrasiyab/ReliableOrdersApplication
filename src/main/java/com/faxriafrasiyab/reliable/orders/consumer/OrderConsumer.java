package com.faxriafrasiyab.reliable.orders.consumer;

import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {


    @KafkaListener(topics = "${app.topics.orders}", groupId = "reliable-orders")
    public void consume(OrderCreatedEvent orderConsumeEvent) {
        System.out.println("Order Consume Event: " + orderConsumeEvent);
    }

}
