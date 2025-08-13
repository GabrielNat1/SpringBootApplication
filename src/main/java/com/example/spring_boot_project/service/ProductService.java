package com.example.spring_boot_project.service;

import com.example.spring_boot_project.exceptions.ResourceNotFound;
import com.example.spring_boot_project.model.Product;
import com.example.spring_boot_project.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found with id: " + id));

    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFound("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

}
