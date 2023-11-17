package project.intro2se.ticketify.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Food {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;
    private String image;
    @ManyToMany
    @JoinTable(name = "transaction",
            joinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    )
    private List<Transaction> transaction;

}
