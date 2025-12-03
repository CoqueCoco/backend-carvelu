package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartItemRepository cartRepo;
    private final UserRepository userRepo;

    @Transactional
    public Order checkout(String email) {

        User user = userRepo.findByEmail(email).orElseThrow();
        List<CartItem> cart = cartRepo.findByUser(user);

        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        Order order = Order.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        order = orderRepo.save(order);

        for (CartItem item : cart) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .price(item.getProduct().getPrice())
                    .build();

            items.add(orderItem);

            total += item.getQuantity() * item.getProduct().getPrice();
        }

        order.setItems(items);
        order.setTotal(total);

        orderRepo.save(order);

        cartRepo.deleteAll(cart);

        return order;
    }

    public List<Order> getMyOrders(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        return orderRepo.findByUser(user);
    }

    public List<Order> getAllOrdersForAdmin() {
        return orderRepo.findAll();
    }
}
