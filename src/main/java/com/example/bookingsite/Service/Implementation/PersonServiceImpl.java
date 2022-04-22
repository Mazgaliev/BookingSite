package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Dto.PersonDto;
import com.example.bookingsite.Model.Enum.Role;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Exception.UsernameAlreadyExistsException;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    //Private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<PersonDto> createPerson(String name, String surname, String username, String password, String phoneNumber, Role role) {
        if (this.personRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
//        String encodedPassword = PasswordEncoder.encode(password);
        Person person = new Person(name, surname, username, password, phoneNumber, role);

        this.personRepository.save(person);

        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        return Optional.of(personDto);
    }

    @Override
    public Optional<PersonDto> update(Long personId, String name, String surname, String username, String password, String phoneNumber, Role role) {
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
        return Optional.of(personDto);
    }

    @Override
    public Optional<PersonDto> findById(Long id) {
        Person person = this.personRepository.findById(id).orElseThrow(PersonDoesNotExistException::new);
        PersonDto personDto = this.modelMapper.map(person, PersonDto.class);
        return Optional.of(personDto);
    }

    @Override
    public Optional<PersonDto> findByUsername(String username) {
        Person person = this.personRepository.findByUsername(username).orElseThrow(PersonDoesNotExistException::new);
        PersonDto personDto = modelMapper.map(person, PersonDto.class);
        return Optional.of(personDto);
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
}
