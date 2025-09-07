package com.faxriafrasiyab.reliable.orders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerEmail;
    private BigDecimal amount;
    private String currency;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}

