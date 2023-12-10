package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String image;
    @OneToMany(mappedBy = "food")
    @JsonBackReference
    private List<FoodOrderLine> foodOrderLines;

}
