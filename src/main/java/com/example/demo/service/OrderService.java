package com.example.demo.service;

import com.example.demo.events.OrderEvent;
import com.example.demo.model.OrderEntity;
import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository,
                        KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public OrderEntity placeOrder(Long userId, Long productId, Integer quantity) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        OrderEntity order = new OrderEntity(user, product, quantity, totalAmount, Instant.now());
        OrderEntity saved = orderRepository.save(order);

        OrderEvent event = new OrderEvent(saved.getId(), user.getId(), product.getId(), quantity, totalAmount, saved.getCreatedAt());
        kafkaTemplate.send(topic, "order-" + saved.getId(), event);
        return saved;
    }

    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }
}
