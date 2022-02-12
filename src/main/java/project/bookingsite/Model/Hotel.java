package project.bookingsite.Model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Hotel extends Place {
    private Integer rooms;

    private Integer freeRooms;

    public Hotel(String name, String location, String contactNumber, Integer rooms, Integer freeRooms) {
        super(name, location, contactNumber);
        this.rooms = rooms;
        this.freeRooms = freeRooms;
    }

    public Hotel() {

    }
}
