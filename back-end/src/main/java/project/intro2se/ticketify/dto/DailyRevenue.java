package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor
@Getter
public class DailyRevenue {
    private LocalDate date;
    private BigDecimal totalRevenue;
}
