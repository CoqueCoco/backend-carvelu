package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la orden
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Relación con producto
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private Double price;
}
