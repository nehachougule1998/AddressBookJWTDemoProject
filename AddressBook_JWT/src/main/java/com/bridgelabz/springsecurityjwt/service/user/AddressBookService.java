package com.bridgelabz.springsecurityjwt.service.user;

import com.bridgelabz.springsecurityjwt.entity.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.entity.UserNameOtpData;
import com.bridgelabz.springsecurityjwt.exception.AddressBookException;
import com.bridgelabz.springsecurityjwt.repository.IAddressBookRepository;
import com.bridgelabz.springsecurityjwt.repository.IUserNameRespository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class AddressBookService implements IAddressBookService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    IAddressBookRepository iAddressBookRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    IUserNameRespository serviceOfOtp;

    @Autowired
    IUserNameRespository iUserNameService;


    @Override
    public AddressBookData createAddressBookData(AddressBookDTO addressBookDTO) {
        AddressBookData user = modelMapper.map(addressBookDTO, AddressBookData.class);
        int otps = (int) Math.floor(Math.random() * 1000000);
        String otp = String.valueOf(otps);
        UserNameOtpData userNameOtp = new UserNameOtpData(addressBookDTO.username, otp);
        serviceOfOtp.save(userNameOtp);//otp is saved
        System.out.println("Mail has sent .....!!!!");
        emailSenderService.sendEmail(user.getEmail(), "OTP for Registration", otp);
        return iAddressBookRepository.save(user);//save user data
    }

    @Override
    public AddressBookData getAddressBookById(long personId) {
        return iAddressBookRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Address Book Contact Id not Found!!!"));
    }

    @Override
    public AddressBookData editAddressBookData(long personId, AddressBookDTO addressBookDTO) {
        AddressBookData addressBookData = this.getAddressBookById(personId);
        modelMapper.map(addressBookDTO, addressBookData);
        int otps = (int) Math.floor(Math.random() * 1000000);
        String otp = String.valueOf(otps);
        UserNameOtpData userNameOtp = new UserNameOtpData(addressBookDTO.username, otp);
        serviceOfOtp.save(userNameOtp);
        emailSenderService.sendEmail(addressBookData.getEmail(), "OTP for Updating Details.", otp);
        iAddressBookRepository.save(addressBookData);
        return addressBookData;
    }

    @Override
    public void deleteAddressBookData(long personId) {
        AddressBookData addressBookData = this.getAddressBookById(personId);
        iAddressBookRepository.delete(addressBookData);
    }

    public List<AddressBookData> getContactsByCity() {
        return iAddressBookRepository.findContactsByCity();
    }

    public List<AddressBookData> getContactsByState() {
        return iAddressBookRepository.findContactsByState();
    }
    @Override
    public Boolean verifyOtp(String username, String otp) {
        UserNameOtpData serverOtp = iUserNameService.findByUsername(username);

        if (otp == null)
            return false;
        if(!(otp.equals(serverOtp.getOtp())))
            return false;
        iAddressBookRepository.changeVerified(username); //when otp successfull then verified change to true
        iUserNameService.deleteEntry(username);
        return true;
    }

    @Override
    public Boolean isVerified(String username) {
        return iAddressBookRepository.isVerified(username);
    }
}





