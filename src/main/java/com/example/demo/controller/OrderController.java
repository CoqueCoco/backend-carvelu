package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.CheckoutResponse;
import com.example.demo.service.OrderService;
import java.security.Principal;
import java.util.List;
import com.example.demo.entity.Order;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public CheckoutResponse checkout(Principal principal) {
        // Solo necesitamos que esté logueado, no importa el rol
        return orderService.checkout(principal.getName());
    }
    
    @GetMapping("/mine")
    public List<Order> myOrders(Principal principal) {
         return orderService.getMyOrders(principal.getName());
    }
}