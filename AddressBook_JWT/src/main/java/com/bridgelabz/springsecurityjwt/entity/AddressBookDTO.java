package com.bridgelabz.springsecurityjwt.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AddressBookDTO {

    public String username;
    public String password;
    public String role;
    public String email;
    public boolean enabled;
    private int personId;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "First name is Invalid")
    private String firstName;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "First name is Invalid")
    private String lastName;
    private String phoneNumber;
    @NotEmpty(message="address cannot be empty")
    private String address;
    @NotEmpty(message="city cannot be empty")
    private String city;
    @NotEmpty(message="state cannot be empty")
    private String state;
    @NotNull(message = "zip cannot be null")
    private int zip;
}
