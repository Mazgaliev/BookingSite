package project.bookingsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookingsite.Model.Hotel;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByName(String name);

    Optional<Hotel> findByContactNumber(String contactNum);
}
