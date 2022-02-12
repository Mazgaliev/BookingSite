package project.bookingsite.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Hotel extends Place {

    private Integer standardRooms;

    private Integer vipRooms;

    private Integer standardPrice;

    private Integer vipPrice;
    @OneToMany
    private List<Room> rooms;

    @ManyToMany
    List<Reservation> reservationList;

    public Hotel(String name, String location, String contactNumber,
                 Integer standardRooms, Integer vipRooms, Integer standardPrice, Integer vipPrice) {
        super(name, location, contactNumber);
        this.vipRooms = vipRooms;
        this.standardRooms = standardRooms;
        this.standardPrice = standardPrice;
        this.vipPrice = vipPrice;
        rooms = new ArrayList<>();
        for (int i = 0; i < standardRooms; i++) {
            rooms.add(new Room(RoomType.STANDARD));
        }
        for (int i = 0; i < vipRooms; i++) {
            rooms.add(new Room(RoomType.VIP));
        }
    }

    public Hotel() {

    }
}
