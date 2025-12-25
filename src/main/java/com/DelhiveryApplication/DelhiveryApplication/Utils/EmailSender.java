package com.DelhiveryApplication.DelhiveryApplication.Utils;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String from;


    public String sendEmail(String recipient,String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
        return "mail Sent successfully";
    }

    public boolean verifyMail(String previous, String fromUser){
        if(previous.equals(fromUser)){
            return true;
        }else{
            return false;
        }
    }
}