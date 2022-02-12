package project.bookingsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookingsite.Model.Villa;

import java.util.Optional;

public interface VillaRepository extends JpaRepository<Villa, Long> {
    Optional<Villa> findByName(String name);

    Optional<Villa> findByContactNumber(String contactnum);
}
