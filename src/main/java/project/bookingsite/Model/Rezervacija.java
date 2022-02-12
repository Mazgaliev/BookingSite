package project.bookingsite.Model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
//TODO reshi go ebanij error wtf
@Data
@Entity
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime from;

    private LocalDateTime to;

    public Rezervacija(LocalDateTime from, LocalDateTime to) {

        this.from = from;
        this.to = to;
    }

    public Rezervacija() {

    }
}
