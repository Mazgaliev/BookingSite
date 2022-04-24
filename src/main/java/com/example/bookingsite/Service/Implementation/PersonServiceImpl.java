package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Exception.InvalidUsernameOrPasswordException;
import com.example.bookingsite.Model.Exception.PasswordsDoNotMatchException;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Exception.UsernameAlreadyExistsException;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    //Private final PasswordEncoder passwordEncoder;
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
        Person person = new Person(name, surname, username, encodedPassword, phoneNumber, role);

        this.personRepository.save(person);

        return person;
    }

    @Override
    public PersonDto update(Long personId, String name, String surname, String username, String password, String phoneNumber, Role role) {
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        person.setName(name);
        if (this.personRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException();
        else {
            person.setUsername(username);
        }
        person.setSurname(surname);
        person.setUserRole(role);
        person.setPhoneNumber(phoneNumber);
        person.setPassword(password);

        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        return personDto;
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
