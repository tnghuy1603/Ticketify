package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.domain.Ticket;
import project.intro2se.ticketify.service.TicketService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    @GetMapping()
    public ResponseEntity<List<Ticket>> findTicketByMovie(@RequestParam(name = "showtime") Long showtimeId){
        return ResponseEntity.ok(ticketService.findTicketsOfShowTime(showtimeId));
    }
    @PostMapping("/generate-ticket/{showtime-id}")
    public ResponseEntity<List<Ticket>> generateTicket(@PathVariable("showtime-id") Long showTimeId,
                                                       @RequestParam("price")BigDecimal price){

        return ResponseEntity.ok(ticketService.generateTicket(showTimeId, price));
    }
    @MessageMapping("/select-ticket")
    @SendTo("public/book-ticket")
    public String testingUser(String msg){
        return msg;
    }


}
