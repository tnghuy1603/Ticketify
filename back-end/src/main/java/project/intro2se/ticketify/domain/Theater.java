package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Theater {
    @Id
    private Long id;
    private String address;
    private String name;
    private String phoneNumber;
    @OneToMany(mappedBy = "theater")
    @JsonBackReference
    private Set<Room> rooms;

}
