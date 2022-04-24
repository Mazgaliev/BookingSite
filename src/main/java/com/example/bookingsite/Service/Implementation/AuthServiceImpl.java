package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Service.AuthService;
import com.example.bookingsite.Service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;

    public AuthServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new PersonDoesNotExistException();
        }
        return this.personRepository.findByUsernameAndPassword(username, password).orElseThrow(PersonDoesNotExistException::new);
    }
}
