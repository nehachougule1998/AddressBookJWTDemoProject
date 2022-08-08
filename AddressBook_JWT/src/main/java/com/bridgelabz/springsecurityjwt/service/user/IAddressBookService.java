package com.bridgelabz.springsecurityjwt.service.user;

import com.bridgelabz.springsecurityjwt.entity.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;

import java.util.List;

public interface IAddressBookService {

    AddressBookData createAddressBookData(AddressBookDTO addressBookDTO);

    AddressBookData getAddressBookById(long personId);

    AddressBookData editAddressBookData(long personId, AddressBookDTO addressBookDTO);

    void deleteAddressBookData(long personId);


    List<AddressBookData> getContactsByState();

    List<AddressBookData> getContactsByCity();

    Boolean verifyOtp(String username, String otp);

    Boolean isVerified(String username);
}

