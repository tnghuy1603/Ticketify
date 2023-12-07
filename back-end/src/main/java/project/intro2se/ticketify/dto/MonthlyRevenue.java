package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
@AllArgsConstructor
@Getter
public class MonthlyRevenue {
    private YearMonth yearMonth;
    private List<DailyRevenue> dailyRevenues;
    BigDecimal total;
}
