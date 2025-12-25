package com.DelhiveryApplication.DelhiveryApplication.Controller;

import com.DelhiveryApplication.DelhiveryApplication.Data.OrderDtos.PlaceOrderDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.OtpVerification;
import com.DelhiveryApplication.DelhiveryApplication.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeorder")
    public ResponseEntity<?> placeOrder (@RequestBody PlaceOrderDTO orderDTO) throws BadRequestException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String senderMail = authentication.getName();
        if(Objects.equals(senderMail,orderDTO.getReceiverMail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error","Sender And Receiver Cannot be same"));
        }
        return ResponseEntity.ok(orderService.palceOrder(orderDTO,senderMail));
    }

    @PostMapping("/conformOrder")
    public String conformOrder(@RequestBody OtpVerification otpbody){

        return orderService.conformOrder(otpbody);
    }

}
