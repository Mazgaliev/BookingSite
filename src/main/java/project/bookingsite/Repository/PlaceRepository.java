package project.bookingsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookingsite.Model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
