package project.bookingsite.Model;

import javax.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long roomId;

    @Enumerated(value = EnumType.STRING)
    private RoomType type;

    private Boolean isReserved;

    public Room(RoomType type) {
        this.type = type;
        isReserved = false;
    }

    public Room() {

    }
}
