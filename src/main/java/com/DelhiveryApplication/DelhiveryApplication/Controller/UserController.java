package com.DelhiveryApplication.DelhiveryApplication.Controller;

import com.DelhiveryApplication.DelhiveryApplication.Data.LoginDTO;
import com.DelhiveryApplication.DelhiveryApplication.Data.OtpVerification;
import com.DelhiveryApplication.DelhiveryApplication.Data.User;
import com.DelhiveryApplication.DelhiveryApplication.Service.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserAccount userAccount;


    @PostMapping("/createUser")
    public String createNewUser(@RequestBody User user){
        return userAccount.createNewUser(user) ;
    }

    @PostMapping("/verifyUser")
    public ResponseEntity<?> verifyUser(@RequestBody OtpVerification otpBody){
        return userAccount.verifyUser(otpBody);
    }

//    @PostMapping("/forgetpass")
//    public String forgetPassword(@RequestBody ForgotPassDTO fpassdto){
//
//    }

}
