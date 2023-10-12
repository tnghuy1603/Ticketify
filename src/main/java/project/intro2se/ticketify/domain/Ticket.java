package project.intro2se.ticketify.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "show_time_id")
    private ShowTime showTime;

}
