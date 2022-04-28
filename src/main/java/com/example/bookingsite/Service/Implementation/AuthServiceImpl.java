package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Enum.AuthenticationType;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Service.AuthService;
import com.example.bookingsite.Service.PersonService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void updateAuthenticationType(String username, String oauth2ClientName) {
        AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
            Optional<Person> existUser = personRepository.findByUsername(username);

            if (existUser.isEmpty()) {
                Person newUser = new Person();
                newUser.setUsername(username);
                newUser.setAuthType(AuthenticationType.GOOGLE);
                newUser.setEnabled(true);
                newUser.setUserRole(Role.ROLE_OWNER);

                personRepository.save(newUser);
            }
            else {
                existUser.get().setAuthType(AuthenticationType.GOOGLE);
                //personRepository.updateAuthenticationType(username, authType);
            }
    }
}
