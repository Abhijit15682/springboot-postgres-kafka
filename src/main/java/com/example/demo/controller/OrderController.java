package com.example.demo.controller;

import com.example.demo.model.OrderEntity;
import com.example.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderEntity> placeOrder(@RequestParam Long userId,
                                                  @RequestParam Long productId,
                                                  @RequestParam Integer quantity) {
        return ResponseEntity.ok(orderService.placeOrder(userId, productId, quantity));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> listOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
