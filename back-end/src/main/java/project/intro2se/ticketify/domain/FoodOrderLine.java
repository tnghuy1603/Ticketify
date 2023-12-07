package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    @JsonManagedReference
    private Food food;
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    @JsonManagedReference
    private Transaction transaction;

}
