package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private boolean booked;
    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    @JsonManagedReference
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "show_time_id", referencedColumnName = "id")
    @JsonManagedReference
    private ShowTime showTime;
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    @JsonManagedReference
    private Transaction transaction;



}
