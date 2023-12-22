package project.intro2se.ticketify.controller;

import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.Transaction;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.BookingRequest;
import project.intro2se.ticketify.service.TransactionService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("book-at-counter")
    public ResponseEntity<Transaction> bookAtCounter(@RequestBody BookingRequest bookingRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.status(201).body(transactionService.bookAtTicketCounter(bookingRequest, user));
    }
    @GetMapping("confirm-booking")
    public ResponseEntity<?> confirmBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(transactionService.confirmBooking(bookingRequest));
    }
    @GetMapping("daily-revenue")
    public ResponseEntity<?> getDailyRevenue(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok(transactionService.calculateDailyRevenue(date));
    }
    @GetMapping("monthly-revenue")
    public ResponseEntity<?> getMonthlyRevenue(@RequestParam(name = "year")int year, @RequestParam(name = "month") int month){
        YearMonth yearMonth = YearMonth.of(year, month);
        return ResponseEntity.ok(transactionService.calculateMonthlyRevenue(yearMonth));
    }

    @GetMapping("history")
    public ResponseEntity<?> getMonthlyRevenue(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(transactionService.findAllByUser(user));
    }







}
