package com.bridgelabz.springsecurityjwt.dto;

import lombok.Data;

@Data
public class UserNameOtpDTO {
    private String username;
    private String otp;
}