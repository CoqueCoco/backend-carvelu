package com.example.demo.dto;


import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
}