package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"start_at", "movie_id", "room_id"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode(exclude = {"id", "endAt"})
@Builder
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_at")
    private LocalDateTime startAt;
    @Column(name = "end_at")
    private LocalDateTime endAt;
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonManagedReference
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonManagedReference
    private Room room;
    


}