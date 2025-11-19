package com.example.Lab7.service;

import com.example.productinventory.model.Product;
import com.example.productinventory.repository.ProductRepository;
import com.example.productinventory.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        log.info("ProductService initialized with {} sample products", productRepository.findAll().size());
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.debug("Retrieved {} products from repository", products.size());
        return products;
    }

    public Product getProductById(Long id) {
        log.debug("Looking for product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product with id {} not found", id);
                    return new ProductNotFoundException(id);
                });
    }

    public Product createProduct(Product product) {
        log.info("Creating new product: {}", product);
        Product savedProduct = productRepository.save(product);
        log.info("Successfully created product with id: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product updateProduct(Long id, Product productDetails) {
        log.debug("Updating product with id: {}", id);

        // Check if product exists
        if (!productRepository.existsById(id)) {
            log.warn("Attempted to update non-existent product with id: {}", id);
            throw new ProductNotFoundException(id);
        }

        Product productToUpdate = Product.builder()
                .id(id)
                .name(productDetails.getName())
                .price(productDetails.getPrice())
                .build();

        Product updatedProduct = productRepository.save(productToUpdate);
        log.info("Successfully updated product: {}", updatedProduct);
        return updatedProduct;
    }

    public void deleteProduct(Long id) {
        log.debug("Attempting to delete product with id: {}", id);

        if (!productRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent product with id: {}", id);
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
        log.info("Successfully deleted product with id: {}", id);
    }
}