package project.intro2se.ticketify.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    @Check(constraints = "status IN ('Upcoming', 'On going', 'Over')")
    private String status;
    private String language;
    private String cast;
    private String poster;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "movie")
    private Set<ShowTime> showTimes;
}
