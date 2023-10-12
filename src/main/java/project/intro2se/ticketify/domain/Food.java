package project.intro2se.ticketify.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Food {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;

}
