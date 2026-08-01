INSERT INTO users (email, full_name)
SELECT 'alice@example.com', 'Alice Johnson'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'alice@example.com');

INSERT INTO users (email, full_name)
SELECT 'bob@example.com', 'Bob Smith'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'bob@example.com');

INSERT INTO products (sku, name, price)
SELECT 'SKU-1001', 'Laptop', 999.99
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'SKU-1001');

INSERT INTO products (sku, name, price)
SELECT 'SKU-1002', 'Mouse', 24.50
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'SKU-1002');

INSERT INTO products (sku, name, price)
SELECT 'SKU-1003', 'Keyboard', 89.00
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'SKU-1003');
