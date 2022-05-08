package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Exception.*;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Person register(String name, String surname, String username, String password, String repeatPassword, String phoneNumber, Role role) {

        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if (this.personRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException();

        String encodedPassword = passwordEncoder.encode(password);
        Person person = new Person(name, surname, username, encodedPassword, phoneNumber,role);

        this.personRepository.save(person);

        return person;
    }

    @Override
    public PersonDto update(Long personId, String name, String surname, String username, String password, String repeatPassword, String oldPassword, String phoneNumber) {
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        if (!person.getUsername().equals(username) && this.personRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
        if(password != null) {
            if (!password.equals(repeatPassword))
                throw new PasswordsDoNotMatchException();
            if (!passwordEncoder.matches(oldPassword, person.getPassword()))
                throw new WrongOldPasswordException();
        }

        person.setUsername(username);
        person.setName(name);
        person.setSurname(surname);
        person.setPhoneNumber(phoneNumber);
        if(password != null) {
            person.setPassword(passwordEncoder.encode(password));
        }
        this.personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto findById(Long id) {
        Person person = this.personRepository.findById(id).orElseThrow(PersonDoesNotExistException::new);
        PersonDto personDto = this.modelMapper.map(person, PersonDto.class);
        return personDto;
    }

    @Override
    public PersonDto findByUsername(String username) {
        Person person = this.personRepository.findByUsername(username).orElseThrow(PersonDoesNotExistException::new);
        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        return personDto;
    }

    @Override
    public List<String> getAllRoles() {
        return Arrays.asList("ROLE_OWNER", "ROLE_STANDARD");
    }

    //To test
    public Long findByUsernameTest(String username) {
        Person person = this.personRepository.findByUsername(username).orElseThrow(PersonDoesNotExistException::new);
        return person.getId();
    }

    @Override
    public void deleteUserById(Long id) {
        Person person = this.personRepository.findById(id).orElseThrow(PersonDoesNotExistException::new);
        this.personRepository.delete(person);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.personRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
