package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Override
    public User getByEmailOrThrow(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public User update(Long id, User newData) {
        User u = repo.findById(id).orElseThrow();
        u.setName(newData.getName());
        u.setEmail(newData.getEmail());
        u.setRoles(newData.getRoles());
        return repo.save(u);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}