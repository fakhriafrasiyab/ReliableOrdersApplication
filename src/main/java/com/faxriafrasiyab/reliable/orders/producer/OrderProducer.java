package com.faxriafrasiyab.reliable.orders.producer;

import com.faxriafrasiyab.reliable.orders.config.AppTopics;
import com.faxriafrasiyab.reliable.orders.entity.Order;
import com.faxriafrasiyab.reliable.orders.events.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AppTopics topics;

    public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate, AppTopics topics) {
        this.kafkaTemplate = kafkaTemplate;
        this.topics = topics;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(topics.orders(), String.valueOf(orderCreatedEvent.orderId()), orderCreatedEvent);

        future.whenComplete((metadata, exception) -> {
            if (exception != null) {
                System.err.println("Failed to send OrderCreatedEvent" + exception.getMessage());
            } else {
                System.out.println("OrderCreatedEvent sent successfully TOPIC: " + metadata.getRecordMetadata().topic()
                        + " Partition: " + metadata.getRecordMetadata().partition()
                        + " Offset: " + metadata.getRecordMetadata().offset());
            }
        });

    }

}
