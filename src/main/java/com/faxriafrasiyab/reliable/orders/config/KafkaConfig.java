package com.faxriafrasiyab.reliable.orders.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    public NewTopic ordersTopic(AppTopics topics) {
        return TopicBuilder.name(topics.orders()).partitions(1).replicas(1).build();
    }

    public NewTopic dltTopic(AppTopics topics) {
        return TopicBuilder.name(topics.dlt()).partitions(1).replicas(1).build();
    }

}
