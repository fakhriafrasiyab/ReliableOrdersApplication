package com.faxriafrasiyab.reliable.orders.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic ordersTopic(AppTopics topics) {
        return TopicBuilder.name(topics.orders()).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic dltTopic(AppTopics topics) {
        return TopicBuilder.name(topics.dlt()).partitions(1).replicas(1).build();
    }


    @Bean
    public CommonErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate, AppTopics topics) {

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new TopicPartition(topics.dlt(), record.partition())
        );

        return new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 3));

    }

}
