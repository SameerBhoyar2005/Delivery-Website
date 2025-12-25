package com.DelhiveryApplication.DelhiveryApplication.Data.UserDTOs;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ForgotPassDTO {

    private Long id;

    private String otp;
}
