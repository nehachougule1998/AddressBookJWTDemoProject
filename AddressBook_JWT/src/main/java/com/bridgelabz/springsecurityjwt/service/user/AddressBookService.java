package com.bridgelabz.springsecurityjwt.service.user;

import com.bridgelabz.springsecurityjwt.entity.AddressBookDTO;
import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import com.bridgelabz.springsecurityjwt.exception.AddressBookException;
import com.bridgelabz.springsecurityjwt.repository.IAddressBookRepository;
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

    @Override
    public AddressBookData addUser(AddressBookDTO addressBookDTO) {
        AddressBookData user = modelMapper.map(addressBookDTO, AddressBookData.class);
        return iAddressBookRepository.save(user);
    }


    @Override
    public List<AddressBookData> getUsers() {
        return null;
    }

    @Override
    public AddressBookData createAddressBookData(AddressBookDTO addressBookDTO) {
        AddressBookData addressBookData = modelMapper.map(addressBookDTO, AddressBookData.class);
        log.debug("Emp Data: " +addressBookData.toString());
        iAddressBookRepository.save(addressBookData);
        return addressBookData;
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
        return iAddressBookRepository.save(addressBookData);
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

}



