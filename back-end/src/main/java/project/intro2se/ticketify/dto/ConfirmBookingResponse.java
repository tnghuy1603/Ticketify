package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.intro2se.ticketify.domain.FoodOrderLine;
import project.intro2se.ticketify.domain.Seat;
import project.intro2se.ticketify.domain.Ticket;

import java.math.BigDecimal;
import java.util.List;
@Getter
@AllArgsConstructor
public class ConfirmBookingResponse {
    private List<Ticket> tickets;
    private BigDecimal totalPrice;
    private List<FoodOrderLine> foodOrderLines;
}
