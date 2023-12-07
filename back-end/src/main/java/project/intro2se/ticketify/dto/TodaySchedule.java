package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.domain.ShowTime;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class TodaySchedule {
    private Set<Movie> movies;
    private List<ShowTime> showTime;

}
