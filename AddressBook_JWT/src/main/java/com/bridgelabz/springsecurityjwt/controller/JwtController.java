package com.bridgelabz.springsecurityjwt.controller;

import com.bridgelabz.springsecurityjwt.entity.JwtResponse;
import com.bridgelabz.springsecurityjwt.entity.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.helper.JwtUtil;
import com.bridgelabz.springsecurityjwt.service.CustomUserDetailsService;
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


    //login api accessible to all the users
    //this api authenticates the user
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody AddressBookDTO loginUser) throws Exception {
        System.out.println(loginUser);
        try {
            String username = loginUser.getUsername();
            String password = loginUser.getPassword();

            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, password);
            //it authenticates if user already exists or not;
            //if user exits it gets successfully authenticated and execution goes to line 52
            this.authenticationManager.authenticate(user);


        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("User invalid");
            throw new Exception("Bad Credentials");
        }catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }

        //fine area...
        //if user successfully authenticated then we get the UserDetails for the user and generate token for him
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(loginUser.getUsername());
        String generatedToken = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT" + generatedToken);

        return ResponseEntity.ok(new JwtResponse(generatedToken));
    }
}
