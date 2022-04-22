package com.example.bookingsite.Model.View;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//TODO View doesnt work lmao fuck mosh i nema da mora jbg ke gi transformirme

@Data
@Entity
@Subselect("SELECT * FROM person")
@Immutable
public class PersonView {

    @Id
    @Column(name = "id")
    private Long personId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone_Number")
    private String phoneNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "user_role")
    private Role user_role;

}
