package com.example.demo.events;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderEvent(Long orderId, Long userId, Long productId, Integer quantity, BigDecimal totalAmount, Instant createdAt) {
}
