package com.example.bookingsite.Repository.View;

import com.example.bookingsite.Model.View.PersonView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDtoRepo extends JpaRepository<PersonView, Long> {
}
