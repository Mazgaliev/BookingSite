package project.bookingsite.Model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Restaurant extends Place {

    private Integer tables;

    private Integer freeTables;


    public Restaurant(String name, String location, String contactNumber, Integer tables, Integer freeTables) {
        super(name, location, contactNumber);
        this.tables = tables;
        this.freeTables = freeTables;
    }

    public Restaurant() {

    }
}
