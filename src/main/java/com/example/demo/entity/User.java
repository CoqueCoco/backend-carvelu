package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    
    private Double points = 0.0; 

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
}