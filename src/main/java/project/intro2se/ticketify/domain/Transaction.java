package project.intro2se.ticketify.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private Set<Ticket> tikets;

}
