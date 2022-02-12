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

    private Double standardPrice;

    private Double vipPrice;
//    @OneToMany
//    private List<Room> rooms;

    @ManyToMany
    List<Reservation> reservationList;

    public Hotel(String name, String location, String contactNumber, User owner,
                 Integer standardRooms, Integer vipRooms,
                 Double standardPrice, Double vipPrice) {
        super(name, location, contactNumber, owner);
        this.standardRooms = standardRooms;
        this.vipRooms = vipRooms;
        this.standardPrice = standardPrice;
        this.vipPrice = vipPrice;
//        this.rooms = new ArrayList<>();
//        for (int i = 0; i < standardRooms; i++) {
//            this.rooms.add(new Room(RoomType.STANDARD));
//        }
//        for (int i = 0; i < vipRooms; i++) {
//            this.rooms.add(new Room(RoomType.VIP));
//        }
    }

    public Hotel() {

    }
}
