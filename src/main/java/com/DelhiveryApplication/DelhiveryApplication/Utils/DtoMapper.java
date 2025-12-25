package com.DelhiveryApplication.DelhiveryApplication.Utils;

import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.AssignedOrderDto;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.PlaceOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.ResponseOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.Orders;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public PlaceOrderDTO placedOrders(Orders orders){
        Long orderId = orders.getId();

        Long senderId = orders.getSender().getId();

        Long receiverId = orders.getReceiver().getId();

        String pickupAddress = orders.getPickupAddress();

        String dropAddress = orders.getDropAddress();

        String instructions = orders.getInstructions();

        return new PlaceOrderDTO(orders.getId(),senderId,receiverId,pickupAddress,dropAddress,instructions);
    }

    public ResponseOrderDTO mapToOrder(Orders orders){
        Long orderId = orders.getId();

        Long senderId = orders.getSender().getId();

        Long receiverId = orders.getReceiver().getId();

        String pickupAddress = orders.getPickupAddress();

        String dropAddress = orders.getDropAddress();

        String instructions = orders.getInstructions();

        String status = String.valueOf(orders.getStatus());

        return new ResponseOrderDTO(orderId,senderId,receiverId,pickupAddress,dropAddress,instructions,status);
    }

    public AssignedOrderDto assignedOrderResponse(Orders orders){
        Long orderId = orders.getId();

        Long senderId = orders.getSender().getId();

        Long receiverId = orders.getReceiver().getId();

        String senderMobile = orders.getSender().getMobile();

        String receiverMobile = orders.getReceiver().getMobile();

        String pickupAddress = orders.getPickupAddress();

        String dropAddress = orders.getDropAddress();

        String instructions = orders.getInstructions();

        String status = String.valueOf(orders.getStatus());

        return new AssignedOrderDto(orderId,senderId,receiverId,senderMobile,
                receiverMobile,pickupAddress,dropAddress,instructions,status);
    }

}
