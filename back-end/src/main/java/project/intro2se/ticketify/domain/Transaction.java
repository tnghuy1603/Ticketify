package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime createdAt;
    private BigDecimal total;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ticket> tickets;
    @OneToMany(mappedBy = "transaction")
    @JsonBackReference
    private List<FoodOrderLine> foodOrderLines;


}
