package com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos;

import lombok.Data;

@Data
public class VerifyPickupDropOrderDTO {

    private Long orderId;

    private String otp;
}
