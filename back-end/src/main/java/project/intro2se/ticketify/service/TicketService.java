package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Room;
import project.intro2se.ticketify.domain.Seat;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.domain.Ticket;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.ShowTimeRepository;
import project.intro2se.ticketify.repository.TicketRepository;
import project.intro2se.ticketify.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ShowTimeRepository showTimeRepository;
    private final TransactionRepository transactionRepository;
    public List<Ticket> findTicketsOfShowTime(Long showtimeId){
        return ticketRepository.findByShowTime_Id(showtimeId);
    }
    /*Receptionist book ticket
    Do not need to go through checkout process
    If moviegoer don't have account => set issuer is receptionist*/


    // If ticket is generated for this showtime, do not allow to generate ticket again
    // The constraint is enforced by client side
    public List<Ticket> generateTicket(Long showTimeId, BigDecimal price){
        ShowTime showTime = showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found any showtime with id:" + showTimeId));
        Room room = showTime.getRoom();
        List<Seat> seats = room.getSeats();
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket;
        // Price of couple seats is double than standard seats
        for(Seat seat: seats){
            ticket = Ticket.builder()
                    .booked(false)
                    .showTime(showTime)
                    .seat(seat)
                    .build();
            if(seat.getCategory().equals("Couple")){
                ticket.setPrice(price.multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(10000)));
            } else{
                ticket.setPrice(price);
            }
            tickets.add(ticket);
        }
        return ticketRepository.saveAll(tickets);
    }







}
