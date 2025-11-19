package com.example.Lab7.repository;

import com.example.Lab7.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class Lab7 {

    private final Map<Long, Product> productDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Lab7() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        save(Product.builder().id(idGenerator.getAndIncrement()).name("Laptop Pro").price(1299.99).build());
        save(Product.builder().id(idGenerator.getAndIncrement()).name("Wireless Mouse").price(29.99).build());
        save(Product.builder().id(idGenerator.getAndIncrement()).name("Mechanical Keyboard").price(89.99).build());
    }

    public List<Product> findAll() {
        return new ArrayList<>(productDatabase.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productDatabase.get(id));
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
        }
        productDatabase.put(product.getId(), product);
        return product;
    }

    public void deleteById(Long id) {
        productDatabase.remove(id);
    }

    public boolean existsById(Long id) {
        return productDatabase.containsKey(id);
    }
}