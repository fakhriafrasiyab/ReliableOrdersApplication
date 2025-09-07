package com.faxriafrasiyab.reliable.orders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.topics")
public record AppTopics (String orders, String dlt) {


}
