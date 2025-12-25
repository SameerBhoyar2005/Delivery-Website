package com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos;


import lombok.Data;

@Data
public class ResponseOrderDTO {
    private Long orderId;

    private Long senderId;

    private Long receiverId;

    private String pickupAddress;

    private String dropAddress;

    private String instructions;

    private String status;

    public ResponseOrderDTO(Long id, Long senderId, Long receiverId, String pickupAddress, String dropAddress, String instructions, String status) {
        this.orderId = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.pickupAddress = pickupAddress;
        this.dropAddress = dropAddress;
        this.instructions = instructions;
        this.status = status;
    }
}
