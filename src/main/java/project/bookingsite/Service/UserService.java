package project.bookingsite.Service;

import project.bookingsite.Model.User;

import java.time.LocalDateTime;

public interface UserService {
    //TODO implement this

    public User deleteUser(Long userId);

    public User createUser(String name, String surname, String username, String password, String phoneNumber);
}
