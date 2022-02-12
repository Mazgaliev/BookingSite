package project.bookingsite.Model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private String phoneNumber;
//    @OneToMany
//    private List<Reservation> reservations;

    public User(String name, String surname, String username, String password, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }
}
