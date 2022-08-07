package com.bridgelabz.springsecurityjwt.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AddressBookData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "personId")
    private long personId;
    public String role;

    public boolean enabled;

    private String username;

    private String password;

    @Column(name = "name")
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private int zip;

}
