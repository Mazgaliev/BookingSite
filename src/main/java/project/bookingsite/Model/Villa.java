package project.bookingsite.Model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Villa extends Place {

    private String description;

    private boolean reserved;

    private Long pricePerNight;

    public Villa(String name, String location, String contactNumber, User owner, String description, Long pricePerNight) {
        super(name, location, contactNumber, owner);
        this.description = description;
        this.reserved = false;
        this.pricePerNight = pricePerNight;
    }

    public Villa() {

    }
}
