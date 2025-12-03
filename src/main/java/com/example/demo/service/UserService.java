package com.example.demo.service;


import java.util.List;

import com.example.demo.entity.User;

public interface UserService {
    User getByEmailOrThrow(String email);
    List<User> getAll();
    User update(Long id, User newData);
    void delete(Long id);
}