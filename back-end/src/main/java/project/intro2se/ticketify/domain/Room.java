package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int roomNumber;
    @OneToMany(mappedBy = "room")
    @JsonBackReference
    private List<Seat> seats;
    @ManyToOne
    @JoinColumn(name = "theater_id", referencedColumnName = "id")
    @JsonManagedReference
    private Theater theater;
}
