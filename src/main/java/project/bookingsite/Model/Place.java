package project.bookingsite.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

@Setter
@Getter
@MappedSuperclass
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String location;

    private String contactNumber;

    @ManyToOne
    private User owner;


    public Place(String name, String location, String contactNumber, User owner) {
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
        this.owner = owner;
    }

    public Place() {

    }
}
