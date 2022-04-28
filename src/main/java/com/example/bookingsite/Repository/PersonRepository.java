package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Enum.AuthenticationType;
import com.example.bookingsite.Model.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"owns"})

    Optional<Person> findByUsernameAndPassword(String username, String password);

    Optional<Person> findByUsername(String username);

    @Modifying
    @Query("UPDATE Person u SET u.authType = ?2 WHERE u.username = ?1")
    public void updateAuthenticationType(String username, AuthenticationType authType);

}
