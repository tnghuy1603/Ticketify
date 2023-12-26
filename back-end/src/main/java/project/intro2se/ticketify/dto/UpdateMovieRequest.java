package project.intro2se.ticketify.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Getter
public class UpdateMovieRequest {
    private Long id;
    private String title;
    private String genre;
    private String status;
    private String language;
    private String director;
    private String cast;
    private String poster;
    private String rated;
    private int duration;
    private String trailer;
    private LocalDate openingDay;
    private String story;
}
