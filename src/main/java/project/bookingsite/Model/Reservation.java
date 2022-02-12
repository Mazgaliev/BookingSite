package project.bookingsite.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Reservation {

    @Id
    private Long id;

    private LocalDateTime start;

    private LocalDateTime kraj;
    @ManyToOne
    private Hotel hotelid;
    @Enumerated(EnumType.STRING)
    private RoomType type;

    public Reservation(LocalDateTime start, LocalDateTime kraj, Hotel hotel, RoomType type) {
        this.start = start;
        this.kraj = kraj;
        this.hotelid = hotel;
        this.type = type;
    }

    public Reservation() {

    }
}
