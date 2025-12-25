package com.DelhiveryApplication.DelhiveryApplication.Service;

import com.DelhiveryApplication.DelhiveryApplication.Utils.DtoMapper;
import com.DelhiveryApplication.DelhiveryApplication.Utils.EmailSender;
import com.DelhiveryApplication.DelhiveryApplication.Utils.OrderStatus;
import com.DelhiveryApplication.DelhiveryApplication.Data.DelhiveryAgent;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.AssignedOrderDto;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.VerifyPickupDropOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.PlaceOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.ResponseOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.Orders;
import com.DelhiveryApplication.DelhiveryApplication.Repository.AgentRepository;
import com.DelhiveryApplication.DelhiveryApplication.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AgentAccount {

    private final AgentRepository agentRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final DtoMapper dtoMapper;

    Map<String,DelhiveryAgent> tempAgent = new HashMap<>();

    public String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    String otp = generateOtp();
    String pickupOtp = generateOtp();
    String dropOtp = generateOtp();



    public String createAgentAccount(@NotNull DelhiveryAgent delhiveryAgent) {
        String email = delhiveryAgent.getEmail();
        String encodedPassword = passwordEncoder.encode(delhiveryAgent.getPassword());
        delhiveryAgent.setPassword(encodedPassword);
        tempAgent.put(email,delhiveryAgent);
        return emailSender.sendEmail(email,otp);
    }

    public String verifyAgent(String email, String agentOtp) {
        if(emailSender.verifyMail(otp,agentOtp)){
            DelhiveryAgent verifiedAgent = tempAgent.get(email);
            verifiedAgent.setVerified(true);
            agentRepository.save(verifiedAgent);
            tempAgent.remove(email);
            return emailSender.sendEmail(email,"YOUR ACCOUNT VERIFIED SUCCESSFULLY");
        }else {
            return "AGENT NOT VERIFIED CHECK OTP";
        }
    }


    public ResponseEntity<?> unAssignedOrders() {
        List<Orders> orders = orderRepository.findAllBystatus(OrderStatus.valueOf("Ordered"));
        if(orders.isEmpty()){
            return new ResponseEntity<>("There are no Orders Placed Yet",HttpStatus.NO_CONTENT);
        }
        List<PlaceOrderDTO> placedOrdered = orders.stream()
                .map(dtoMapper::placedOrders)
                .toList();
        return new ResponseEntity<>(placedOrdered,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> selectOrder(@NotNull ResponseOrderDTO responseOrderDTO,String agentMail) {
       Optional<Orders> order = orderRepository.findById(responseOrderDTO.getOrderId());
       Optional<DelhiveryAgent> optionalAgent = Optional.ofNullable(agentRepository.findByEmail(agentMail));
        DelhiveryAgent agent = optionalAgent.get();
       Orders orderEntity = order.get();
       String senderEmail = orderEntity.getSender().getEmail();
       emailSender.sendEmail(senderEmail,"DELIVERY AGENT ASSIGNED TO YOU SHARE THIS OTP TO AGENT "+ pickupOtp);
       orderEntity.setPickupOtp(pickupOtp);
       orderEntity.setStatus(OrderStatus.Assigned);
       orderEntity.setAgent(agent);

        AssignedOrderDto assignedParcel = dtoMapper.assignedOrderResponse(orderEntity);
       return new ResponseEntity<>(assignedParcel,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> pickupOrder(@NotNull VerifyPickupDropOrderDTO pickupOrderDTO) {
        Optional<Orders> optionalOrder = orderRepository.findById(pickupOrderDTO.getOrderId());
        Orders order = optionalOrder.get();
        Long senderId = order.getSender().getId();
        String savedPickupOtp =  order.getPickupOtp();
        String senderName = order.getSender().getName();
        String receiverEmail = order.getReceiver().getEmail();
        if (!savedPickupOtp.equals(pickupOrderDTO.getOtp())){
            return new ResponseEntity<>("WRONG OTP",HttpStatus.UNAUTHORIZED);
        }
        order.setStatus(OrderStatus.pickuped);
        order.setPickupTime(LocalDateTime.now());
        order.setDropOtp(dropOtp);
        emailSender.sendEmail(receiverEmail,"YOU HAVE A ORDER FROM "+ senderName +" "+ senderId + ". PLEASE CHECK YOUR ORDER AND VERIFY BY OTP " + dropOtp);
        return new ResponseEntity<>("ORDER PICKED UP SUCCESSFULLY", HttpStatus.OK);
    }

    public ResponseEntity<?> dropOrder(@NotNull VerifyPickupDropOrderDTO dropOrderDTO) {
        Optional<Orders> optionalOrder = orderRepository.findById(dropOrderDTO.getOrderId());
        Orders order = optionalOrder.get();
        String savedDropOtp = order.getDropOtp();
        if (!savedDropOtp.equals(dropOrderDTO.getOtp())){
            return new ResponseEntity<>("WRONG OTP",HttpStatus.UNAUTHORIZED);
        }
        order.setStatus(OrderStatus.received);
        order.setDropTime(LocalDateTime.now());
        return new ResponseEntity<>("ORDER DROPPED SUCCESSFULLY", HttpStatus.OK);
    }
}
