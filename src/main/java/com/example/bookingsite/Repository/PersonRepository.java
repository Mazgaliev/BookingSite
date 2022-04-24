package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsernameAndPassword(String username, String password);

    Optional<Person> findByUsername(String username);
}
