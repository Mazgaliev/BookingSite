package project.bookingsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookingsite.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);
}
