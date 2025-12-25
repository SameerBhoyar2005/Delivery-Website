package com.DelhiveryApplication.DelhiveryApplication.Service;

import com.DelhiveryApplication.DelhiveryApplication.Utils.EmailSender;
import com.DelhiveryApplication.DelhiveryApplication.Utils.OrderStatus;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.PlaceOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.Orders;
import com.DelhiveryApplication.DelhiveryApplication.Data.OtpVerification;
import com.DelhiveryApplication.DelhiveryApplication.Data.User;
import com.DelhiveryApplication.DelhiveryApplication.Repository.OrderRepository;
import com.DelhiveryApplication.DelhiveryApplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final EmailSender emailSender;


    Map<String,Orders> unVerifedOrders = new HashMap<>();

    public String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    private final String senderOtp = generateOtp();
    private final String receiverOtp = generateOtp();

    public String palceOrder(PlaceOrderDTO orderDTO,String senderMail) throws BadRequestException {


      User sender = userRepository.findByEmail(senderMail);
      User receiver = userRepository.findByEmail(orderDTO.getReceiverMail());

      Orders order = new Orders();
      order.setSender(sender);
      order.setReceiver(receiver);
      order.setPickupAddress(orderDTO.getPickupAddress());
      order.setDropAddress(orderDTO.getDropAddress());
      order.setInstructions(orderDTO.getInstructions());
      order.setStatus(OrderStatus.Ordered);
      unVerifedOrders.put(senderOtp,order);

      return emailSender.sendEmail(sender.getEmail(),"Otp for parcel conformation"+senderOtp);
    }

    public String conformOrder(OtpVerification otpVerification){
        if(otpVerification.getOtp().equals(senderOtp)){
            orderRepository.save(unVerifedOrders.get(senderOtp));
            unVerifedOrders.remove(receiverOtp);
            return " Parcel Verified Successfully ";
        }else {
            return " Parcel Not verified ";
        }
    }

}
