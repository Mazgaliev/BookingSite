package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface PersonService extends UserDetailsService {

    Person register(String name, String surname, String username, String password, String repeatPassword, String phoneNumber, Role role);

    PersonDto update(Long personId, String name, String surname, String username, String password, String phoneNumber, Role role);

    PersonDto findById(Long id);

    PersonDto findByUsername(String username);

    void deleteUserById(Long id);


}
