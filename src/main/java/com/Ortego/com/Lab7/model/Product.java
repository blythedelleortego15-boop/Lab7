package com.example.Lab7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Double price;

    // Custom toString for better logging
    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%.2f}", id, name, price);
    }
}