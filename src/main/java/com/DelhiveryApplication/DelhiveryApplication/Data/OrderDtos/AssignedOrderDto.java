package com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos;

import lombok.Data;

@Data
public class AssignedOrderDto {

    private Long orderId;

    private Long senderId;

    private Long receiverId;

    private String pickupLocation;

    private String dropLocation;

    private String senderMobile;

    private String receiverMobile;

    private String status;

    private String instructions;

    public AssignedOrderDto(Long orderId, Long senderId, Long receiverId, String pickupLocation, String dropLocation, String senderMobile, String receiverMobile, String status, String instructions) {
        this.orderId = orderId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.senderMobile = senderMobile;
        this.receiverMobile = receiverMobile;
        this.status = status;
        this.instructions = instructions;
    }
}
