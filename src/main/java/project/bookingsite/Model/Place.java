package project.bookingsite.Model;

import javax.persistence.*;

@MappedSuperclass
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String location;

    private String contactNumber;

    @OneToOne
    private User owner;

    public Place(String name, String location, String contactNumber) {
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    public Place() {

    }
}
