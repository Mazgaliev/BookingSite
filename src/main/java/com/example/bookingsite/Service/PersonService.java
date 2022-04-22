package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Person;

import java.util.Optional;

public interface PersonService {

    Optional<PersonDto> createPerson(String name, String surname, String username, String password, String phoneNumber, Role role);

    Optional<PersonDto> update(Long personId, String name, String surname, String username, String password, String phoneNumber, Role role);

    Optional<PersonDto> findById(Long id);

    Optional<PersonDto> findByUsername(String username);

    void deleteUserById(Long id);


}
