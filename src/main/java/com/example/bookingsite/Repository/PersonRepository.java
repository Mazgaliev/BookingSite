package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
