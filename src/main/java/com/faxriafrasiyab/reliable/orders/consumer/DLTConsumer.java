package com.faxriafrasiyab.reliable.orders.consumer;

import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DLTConsumer {


    @KafkaListener(topics = "${app.topics.dlt}", groupId = "reliable-orders-dlt")
    public void consume(OrderCreatedEvent failedEvent) {
        System.err.println("DLT Event received: " + failedEvent);
    }

}
