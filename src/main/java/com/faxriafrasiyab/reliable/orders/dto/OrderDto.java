package com.faxriafrasiyab.reliable.orders.dto;

import java.math.BigDecimal;

public record OrderDto(String customerEmail, BigDecimal amount, String currency) {


}
