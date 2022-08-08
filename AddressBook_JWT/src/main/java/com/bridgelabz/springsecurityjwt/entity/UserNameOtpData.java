package com.bridgelabz.springsecurityjwt.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserNameOtpData {

    @Id
    private String username;
    private String otp;

    public UserNameOtpData(String username, String otp) {
        this.username = username;
        this.otp = otp;
    }

    public UserNameOtpData() {
    }
}