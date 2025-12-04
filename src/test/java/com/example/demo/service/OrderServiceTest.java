package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.CheckoutResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepo;
    @Mock
    private CartItemRepository cartRepo;
    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Product product;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        user = new User();
        user.setEmail("test@demo.com");
        user.setPoints(0.0);

        product = new Product();
        product.setPrice(10000.0); // Precio base 10.000

        cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
    }

    @Test
    void testCheckout_CalculatesPointsCorrectly() {
        // GIVEN: Un usuario con 1 producto de 10.000 en el carrito
        when(userRepo.findByEmail("test@demo.com")).thenReturn(Optional.of(user));
        when(cartRepo.findByUser(user)).thenReturn(List.of(cartItem));
        when(orderRepo.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        // WHEN: Ejecutamos el checkout
        CheckoutResponse response = orderService.checkout("test@demo.com");

        // THEN: Debería ganar el 1% de 10.000 = 100 puntos
        assertEquals(100, response.getPointsEarned(), "Los puntos ganados deberían ser el 1% del total");
        verify(userRepo, times(1)).save(user); // Verificar que se guardó el usuario actualizado
    }

    @Test
    void testCheckout_ThrowsException_WhenCartIsEmpty() {
        // GIVEN: Un usuario con carrito vacío
        when(userRepo.findByEmail("test@demo.com")).thenReturn(Optional.of(user));
        when(cartRepo.findByUser(user)).thenReturn(new ArrayList<>());

        // WHEN & THEN: Se espera una excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.checkout("test@demo.com");
        });

        assertEquals("Cart is empty", exception.getMessage());
    }
}