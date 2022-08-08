package com.bridgelabz.springsecurityjwt.controller;


import com.bridgelabz.springsecurityjwt.dto.ResponseDTO;
import com.bridgelabz.springsecurityjwt.dto.UserNameOtpDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.entity.JwtResponse;
import com.bridgelabz.springsecurityjwt.service.user.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class Home {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAddressBookService userService;

    final static String SUCCESS = "Entered Otp is valid, and Registration was successful.";
    final static String FAIL = "Entered OTP was not valid! , Registration failed!, please try again";



    //this api is accessible to authenticated user only
    @RequestMapping("/welcome")
    public String welcome() {
        String text = "This is a private page!!! ";
        text += "Only authorized user can access this page!!!";
        return text;
    }


    //registration api permitted to be accessed by all the users
    @PostMapping("/register")
    public ResponseEntity<AddressBookData> addUser(@Valid @RequestBody AddressBookDTO addressBookDTO) {
        addressBookDTO.setPassword(passwordEncoder.encode(addressBookDTO.getPassword()));
        AddressBookData user = userService.createAddressBookData(addressBookDTO);
        return ResponseEntity.ok(user);
    }


    @GetMapping(value = {"/get/{personId}"})
    public ResponseEntity<ResponseDTO> getAddressBookData(@PathVariable long personId) {
        AddressBookData addressBookData = userService.getAddressBookById(personId);
        ResponseDTO responseDTO = new ResponseDTO("Success Call for Person Id!!!", addressBookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PutMapping(value = {"/edit/{personId}"})
    public ResponseEntity<ResponseDTO> editAddressBookData(@PathVariable long personId,
                                                           @Valid @RequestBody AddressBookDTO addressBookDTO) {
        addressBookDTO.setPassword(passwordEncoder.encode(addressBookDTO.getPassword()));
        AddressBookData addressBookData = userService.editAddressBookData(personId, addressBookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Data UPDATED Successfully!!!", addressBookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping(value = {"/remove/{personId}"})
    public ResponseEntity<ResponseDTO> removeAddressBookData(@PathVariable long personId) {
        userService.deleteAddressBookData(personId);
        ResponseDTO responseDTO = new ResponseDTO("Data DELETED Successfully!!!",
                "ID number: " + personId + "DELETED!!!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping(value = {"/city"})
    public ResponseEntity<ResponseDTO> getContactsByCity() {
        List<AddressBookData> addressBookDataList = userService.getContactsByCity();
        ResponseDTO responseDTO = new ResponseDTO("Success call for City!!!", addressBookDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping(value = {"/state"})
    public ResponseEntity<ResponseDTO> getContactsByState() {
        List<AddressBookData> addressBookDataList = userService.getContactsByState();
        ResponseDTO responseDTO = new ResponseDTO("Success call for State!!!", addressBookDataList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PostMapping({"/verifyotp"})
    public String verifyOtp(@Valid @RequestBody UserNameOtpDTO userNameOtpDTO) {
        String username = userNameOtpDTO.getUsername();
        String otp = userNameOtpDTO.getOtp();
        Boolean isVerifyOtp = userService.verifyOtp(username, otp);
        if (!isVerifyOtp)
            return FAIL;
        return SUCCESS;
    }



}
