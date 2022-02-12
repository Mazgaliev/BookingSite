package project.bookingsite.Service.implementation;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.bookingsite.Model.User;
import project.bookingsite.Model.exceptions.PhoneAlreadyInUseException;
import project.bookingsite.Model.exceptions.UserDoesNotExistException;
import project.bookingsite.Model.exceptions.UsernameAlreadyExistsException;
import project.bookingsite.Repository.UserRepository;
import project.bookingsite.Service.UserService;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User deleteUser(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(UserDoesNotExistException::new);
        userRepository.delete(u);
        return u;
    }

    @Override
    public User createUser(String name, String surname, String username, String password, String phoneNumber) {
        if (userRepository.findByUsername(username) != null)
            throw new UsernameAlreadyExistsException();
        else if (userRepository.findByPhoneNumber(phoneNumber) != null)
            throw new PhoneAlreadyInUseException();
        return new User(name, surname, username, passwordEncoder.encode(password), phoneNumber);
    }

}
