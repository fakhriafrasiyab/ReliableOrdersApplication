package com.faxriafrasiyab.reliable.orders.events;

import java.math.BigDecimal;

public record OrderCreatedEvent(Long orderId, String customerEmail, BigDecimal amount, String currency) {

}
