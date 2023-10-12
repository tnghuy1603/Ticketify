package project.intro2se.ticketify.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Room {
    @Id
    private Long id;
    @OneToMany(mappedBy = "room")
    private Set<Seat> seats;
}
