package project.intro2se.ticketify.controller;

import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.BookingRequest;
import project.intro2se.ticketify.service.TransactionService;

import java.io.IOException;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping
    public ResponseEntity<?> checkout(@RequestBody BookingRequest bookingRequest, @AuthenticationPrincipal User user){
        try {
            return ResponseEntity.status(201).body(transactionService.bookTickets(bookingRequest, user));
        } catch (IOException | MessagingException | WriterException e) {
            throw new RuntimeException(e);
        }
    }



}
