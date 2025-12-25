package com.DelhiveryApplication.DelhiveryApplication.Data;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginDTO {

    private String email;

    private String password;
}
