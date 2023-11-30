package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.intro2se.ticketify.domain.Theater;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String id;
    private String theaterName;
    private String theaterAddress;
    private Long showRoom;
    private String username;
    private String movieTitle;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private String seats;
    private BigDecimal ticketPrice;
    private BigDecimal foodPrice;
    private BigDecimal totalPrice;

}
