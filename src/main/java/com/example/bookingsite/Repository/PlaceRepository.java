package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
