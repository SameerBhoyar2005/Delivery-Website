package com.DelhiveryApplication.DelhiveryApplication.Data;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {
   private String jwt;

   private List<Orders> orders;

}
