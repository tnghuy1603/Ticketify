package project.intro2se.ticketify.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Getter
public class AddMovieRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String genre;
    @NotBlank
    private String status;
    @NotBlank
    private String language;
    @NotBlank
    private String director;
    @NotBlank
    private String cast;
    @NotBlank
    private String rated;
    @Min(value = 0)
    private int duration;
    @NotBlank
    private String trailer;
    private LocalDate openingDay;
    @NotBlank
    private String story;
}
