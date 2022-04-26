package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"owns"})

    Optional<Person> findByUsernameAndPassword(String username, String password);

    Optional<Person> findByUsername(String username);
}
