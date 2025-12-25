package com.DelhiveryApplication.DelhiveryApplication.Data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OtpVerification {

    private String email;

    private String otp;

}
