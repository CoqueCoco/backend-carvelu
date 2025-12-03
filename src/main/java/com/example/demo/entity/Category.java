package com.example.demo.entity;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
}