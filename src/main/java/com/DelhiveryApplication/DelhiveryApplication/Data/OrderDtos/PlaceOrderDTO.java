package com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos;


import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDTO {

    private Long orderId;

    private String receiverMail;

    private String pickupAddress;

    private String dropAddress;

    private String instructions;

    public PlaceOrderDTO(Long id, Long senderId, Long receiverId, String pickupAddress, String dropAddress, String instructions) {
    }
}
