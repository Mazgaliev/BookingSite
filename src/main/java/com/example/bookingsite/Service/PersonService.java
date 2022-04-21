package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Person;

import java.util.Optional;

public interface PersonService {

    Optional<Person> createPerson(String name, String username, String password, String phoneNumber, Role role);

    Optional<Person> update(Long personId, String name, String username, String password, String phoneNumber, Role role);

    Optional<Person> findById(Long id);

    void deleteUserById(Long id);



}
