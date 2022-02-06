package project.bookingsite.Model;

import javax.persistence.Entity;

@Entity
public class Restaurant extends Place {

    private Integer tables;

    private Integer freeSpaces;


}
