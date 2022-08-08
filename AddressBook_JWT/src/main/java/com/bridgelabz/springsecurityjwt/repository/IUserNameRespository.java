package com.bridgelabz.springsecurityjwt.repository;

import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.entity.UserNameOtpData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserNameRespository extends JpaRepository<UserNameOtpData, String> {

    public AddressBookData findByUsername(String username);
}