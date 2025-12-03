package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.config.JwtUtils;
import com.example.demo.entity.CartItem;
import com.example.demo.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtils jwt;

    private String getEmail(String header) {
        return jwt.extractEmail(header.substring(7));
    }

    @GetMapping
    public List<CartItem> getMyCart(@RequestHeader("Authorization") String header) {
        return cartService.getCart(getEmail(header));
    }

    @PostMapping("/add")
    public CartItem add(@RequestHeader("Authorization") String header,
                        @RequestParam Long productId,
                        @RequestParam int quantity) {
        return cartService.addToCart(getEmail(header), productId, quantity);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }

    @DeleteMapping("/clear")
    public void clear(@RequestHeader("Authorization") String header) {
        cartService.clearCart(getEmail(header));
    }
}
