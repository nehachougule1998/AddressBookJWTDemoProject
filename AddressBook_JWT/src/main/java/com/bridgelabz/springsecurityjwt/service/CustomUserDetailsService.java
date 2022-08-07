package com.bridgelabz.springsecurityjwt.service;

import com.bridgelabz.springsecurityjwt.entity.CustomUserDetails;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.repository.IAddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IAddressBookRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AddressBookData user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not Found!!");
        } else {
            return new CustomUserDetails(user);
        }
    }
}
