package com.example.demo.service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    // Obtener todos los items del carrito del usuario
    public List<CartItem> getCart(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepo.findByUserEmail(email);
    }

    // Agregar producto al carrito
    public CartItem addToCart(String email, Long productId, int quantity) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Ver si el producto ya existe en el carrito
        CartItem existing = cartRepo.findByUserEmail(email).stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartRepo.save(existing);
        }

        // Crear nuevo cart item
        CartItem item = CartItem.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .build();

        return cartRepo.save(item);
    }

    // Eliminar un item por id
    public void removeFromCart(Long id) {
        cartRepo.deleteById(id);
    }

    // Limpiar carrito completo
    public void clearCart(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        cartRepo.deleteByUserEmail(email);
    }
}
