package com.faxriafrasiyab.reliable.orders.repository;

import com.faxriafrasiyab.reliable.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
