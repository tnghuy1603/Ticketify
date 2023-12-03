package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.domain.ShowTime;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class ScheduleByTheaterAndDate {
    private LocalDate date;
    private List<Movie> movies;
    private List<ShowTime> showTimes;
}
