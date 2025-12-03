package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Product findByIdOrThrow(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    public Product create(Product p) {
        p.setId(null); // asegurar creación
        return productRepo.save(p);
    }

    public Product update(Long id, Product updated) {
        Product existing = findByIdOrThrow(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setImageUrl(updated.getImageUrl());
        existing.setCategory(updated.getCategory());
        return productRepo.save(existing);
    }

    public void delete(Long id) {
        findByIdOrThrow(id);
        productRepo.deleteById(id);
    }
}