package com.DelhiveryApplication.DelhiveryApplication.Service;


import com.DelhiveryApplication.DelhiveryApplication.Utils.EmailSender;

import com.DelhiveryApplication.DelhiveryApplication.Data.OtpVerification;
import com.DelhiveryApplication.DelhiveryApplication.Data.User;

import com.DelhiveryApplication.DelhiveryApplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserAccount {

    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    Map<String,User> tempUsers = new HashMap<>();

    public String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    String otp=generateOtp();


    public String createNewUser(@NotNull User user){
        String email = user.getEmail();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        tempUsers.put(otp,user);
        return emailSender.sendEmail(email,"OTP FOR ACCOUNT VERIFICATION OF DELIVERY APPLICATION "+otp);
    }


    public ResponseEntity<?> verifyUser(@NotNull OtpVerification otpVerification){
        if(emailSender.verifyMail(otp,otpVerification.getOtp())){
            User verifiedUser = tempUsers.get(otp);
            verifiedUser.setVerified(true);
            userRepository.save(verifiedUser);
             tempUsers.remove(otp);
             User user = userRepository.findByEmail(verifiedUser.getEmail());
            emailSender.sendEmail(otpVerification.getEmail(),"YOUR ACCOUNT VERIFIED AND CREATED SUCCESSFULLY AND YOUR USERID IS " + user.getId());
            return new ResponseEntity<>("ACCOUNT CREATED SUCCESSULLY ", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("WRONG OTP ", HttpStatus.UNAUTHORIZED);
        }
    }
}
