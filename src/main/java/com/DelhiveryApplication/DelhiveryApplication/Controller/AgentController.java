package com.DelhiveryApplication.DelhiveryApplication.Controller;


import com.DelhiveryApplication.DelhiveryApplication.Data.DelhiveryAgent;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.VerifyPickupDropOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.ResponseOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.OtpVerification;
import com.DelhiveryApplication.DelhiveryApplication.Service.AgentAccount;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentAccount agentAccount;

    @PostMapping("/createAccount")
    public String createAgentAccount(@RequestBody DelhiveryAgent delhiveryAgent){
        return agentAccount.createAgentAccount(delhiveryAgent);
    }

    @PostMapping("verifyAgent")
    public String verifyUser(@RequestBody OtpVerification otpVerification){
        return agentAccount.verifyAgent(otpVerification.getEmail(),otpVerification.getOtp());
    }

    @GetMapping("/unAssignedOrder")
    public ResponseEntity<?> unAssignedOrder() {
        return agentAccount.unAssignedOrders();
    }

    @PostMapping("/selectOrder")
    public ResponseEntity<?> selectOrder(@RequestBody ResponseOrderDTO responseOrderDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String mail = authentication.getName();
        return agentAccount.selectOrder(responseOrderDTO,mail);
    }

    @PostMapping("/pickupOrder")
    public ResponseEntity<?> pickupOrder(@RequestBody VerifyPickupDropOrderDTO pickupOrderDTO){
        return agentAccount.pickupOrder(pickupOrderDTO);
    }

    @PostMapping("/dropOrder")
    public ResponseEntity<?> dropOrder(@RequestBody VerifyPickupDropOrderDTO dropOrderDTO){
        return agentAccount.dropOrder(dropOrderDTO);
    }

}
