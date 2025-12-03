package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.config.JwtUtils;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwt;

    private String getEmail(String header) {
        return jwt.extractEmail(header.substring(7));
    }

    @PostMapping("/checkout")
    public Order checkout(@RequestHeader("Authorization") String header) {
        return orderService.checkout(getEmail(header));
    }

    @GetMapping("/mine")
    public List<Order> myOrders(@RequestHeader("Authorization") String header) {
        return orderService.getMyOrders(getEmail(header));
    }

    @GetMapping("/all")
    public List<Order> all() {
        return orderService.getAllOrdersForAdmin();
    }
}
