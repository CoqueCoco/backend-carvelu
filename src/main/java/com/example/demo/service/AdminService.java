package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> bulkCreate(List<Product> products) {
        return productRepo.saveAll(products);
    }
}