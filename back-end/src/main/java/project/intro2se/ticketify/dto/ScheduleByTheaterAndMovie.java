package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.intro2se.ticketify.domain.ShowTime;

import java.util.List;
@AllArgsConstructor
@Getter
public class ScheduleByTheaterAndMovie {
    private List<ShowTime> showTimes;
}
