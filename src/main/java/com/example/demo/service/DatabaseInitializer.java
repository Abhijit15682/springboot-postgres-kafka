package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Order(1)
public class DatabaseInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Initializing database schema and seed data");

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id BIGSERIAL PRIMARY KEY,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    full_name VARCHAR(255) NOT NULL
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS products (
                    id BIGSERIAL PRIMARY KEY,
                    sku VARCHAR(255) NOT NULL UNIQUE,
                    name VARCHAR(255) NOT NULL,
                    price NUMERIC(38,2) NOT NULL
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS orders (
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT NOT NULL REFERENCES users(id),
                    product_id BIGINT NOT NULL REFERENCES products(id),
                    quantity INTEGER NOT NULL,
                    total_amount NUMERIC(38,2) NOT NULL,
                    created_at TIMESTAMP WITH TIME ZONE NOT NULL
                )
                """);

        Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        if (userCount != null && userCount == 0) {
            jdbcTemplate.update("INSERT INTO users (email, full_name) VALUES (?, ?) ON CONFLICT (email) DO NOTHING", "alice@example.com", "Alice Johnson");
            jdbcTemplate.update("INSERT INTO users (email, full_name) VALUES (?, ?) ON CONFLICT (email) DO NOTHING", "bob@example.com", "Bob Smith");
        }

        Integer productCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class);
        if (productCount != null && productCount == 0) {
            jdbcTemplate.update("INSERT INTO products (sku, name, price) VALUES (?, ?, ?) ON CONFLICT (sku) DO NOTHING", "SKU-1001", "Laptop", "999.99");
            jdbcTemplate.update("INSERT INTO products (sku, name, price) VALUES (?, ?, ?) ON CONFLICT (sku) DO NOTHING", "SKU-1002", "Mouse", "24.50");
            jdbcTemplate.update("INSERT INTO products (sku, name, price) VALUES (?, ?, ?) ON CONFLICT (sku) DO NOTHING", "SKU-1003", "Keyboard", "89.00");
        }
    }
}
