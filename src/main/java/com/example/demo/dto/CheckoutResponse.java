package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutResponse {
    private Long orderId;
    private Double totalPaid;
    private Integer pointsEarned;
}