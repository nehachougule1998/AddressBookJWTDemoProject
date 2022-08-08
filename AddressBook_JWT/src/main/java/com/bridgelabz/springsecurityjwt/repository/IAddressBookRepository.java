package com.bridgelabz.springsecurityjwt.repository;

import com.bridgelabz.springsecurityjwt.entity.AddressBookData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface IAddressBookRepository extends JpaRepository<AddressBookData, Long> {

    public AddressBookData findByUsername(String username);

    @Query(value = "select *from address_book_data order by city", nativeQuery = true)
    List<AddressBookData> findContactsByCity();
    @Query (value = "select *from address_book_data order by state", nativeQuery = true)
    List<AddressBookData> findContactsByState();

    @Modifying
    @Transactional
    @Query(value = "update address_book_data set verified=true where username = :username ", nativeQuery = true)
    void changeVerified(String username);

}