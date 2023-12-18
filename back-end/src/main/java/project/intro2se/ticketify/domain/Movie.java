package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    @Check(constraints = "status IN ('Upcoming', 'Ongoing', 'Over')")
    private String status;
    private String language;
    private String director;
    private String cast;
    private String poster;
    private String rated;
    private int duration;
    private String trailer;
    private LocalDate openingDay;
    @Column(columnDefinition = "TEXT")
    private String story;
    @OneToMany(mappedBy = "movie")
    @JsonBackReference
    private Set<ShowTime> showTimes;

}
