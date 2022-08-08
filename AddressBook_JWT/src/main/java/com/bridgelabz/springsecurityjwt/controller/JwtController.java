package com.bridgelabz.springsecurityjwt.controller;

import com.bridgelabz.springsecurityjwt.entity.JwtResponse;
import com.bridgelabz.springsecurityjwt.entity.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.helper.JwtUtil;
import com.bridgelabz.springsecurityjwt.service.CustomUserDetailsService;
import com.bridgelabz.springsecurityjwt.service.user.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AddressBookService addressBookService;


    //login api accessible to all the users
    //this api authenticates the user
    @PostMapping({"/token", "/firstlogin"})
    public ResponseEntity<?> generateToken(@RequestBody AddressBookDTO addressBookDTO) throws Exception {
        System.out.println(addressBookDTO);
        try {
            String username = addressBookDTO.getUsername();
            String password = addressBookDTO.getPassword();
            Boolean isVerified = addressBookService.isVerified(username);
            if (!isVerified)
                return ResponseEntity.ok(new JwtResponse("User not verified, Please do the verification first."));
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(user);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("User invalid");
            throw new Exception("Bad Credentials");
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(addressBookDTO.getUsername());//generate token
        String generatedToken = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT" + generatedToken);

        return ResponseEntity.ok(new JwtResponse(generatedToken));
    }
}