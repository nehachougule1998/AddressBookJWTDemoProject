package com.bridgelabz.springsecurityjwt.repository;


import com.bridgelabz.springsecurityjwt.entity.UserNameOtpData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IUserNameRespository extends JpaRepository<UserNameOtpData, String> {

    public UserNameOtpData findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_name_otp_data WHERE username= :username", nativeQuery = true)
    void deleteEntry(String username);
}