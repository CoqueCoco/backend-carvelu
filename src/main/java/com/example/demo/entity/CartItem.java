package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Uno-usuario / muchos-cart-items
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Uno-producto / muchos-cart-items
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
