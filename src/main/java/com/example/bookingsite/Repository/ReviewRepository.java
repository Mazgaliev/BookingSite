package com.example.bookingsite.Repository;

import com.example.bookingsite.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByPlaceId(Long id);

    Optional<Review> findByPerson_IdAndPlace_Id(Long personId, Long placeId);
}
