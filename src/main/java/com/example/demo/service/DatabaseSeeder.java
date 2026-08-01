package com.example.demo.service;

import com.example.demo.model.ProductEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public DatabaseSeeder(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new UserEntity("alice@example.com", "Alice Johnson"));
            userRepository.save(new UserEntity("bob@example.com", "Bob Smith"));
        }

        if (productRepository.count() == 0) {
            productRepository.save(new ProductEntity("SKU-1001", "Laptop", new BigDecimal("999.99")));
            productRepository.save(new ProductEntity("SKU-1002", "Mouse", new BigDecimal("24.50")));
            productRepository.save(new ProductEntity("SKU-1003", "Keyboard", new BigDecimal("89.00")));
        }
    }
}
