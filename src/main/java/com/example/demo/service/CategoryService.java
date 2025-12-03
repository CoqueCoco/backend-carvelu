package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public Category findByIdOrThrow(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada: " + id));
    }

    public Category create(Category c) {
        c.setId(null);
        return categoryRepo.save(c);
    }

    public Category update(Long id, Category c) {
        Category ex = findByIdOrThrow(id);
        ex.setName(c.getName());
        return categoryRepo.save(ex);
    }

    public void delete(Long id) {
        findByIdOrThrow(id);
        categoryRepo.deleteById(id);
    }
}