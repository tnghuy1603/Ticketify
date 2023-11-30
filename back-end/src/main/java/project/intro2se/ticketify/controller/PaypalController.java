package project.intro2se.ticketify.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.BookingRequest;
import project.intro2se.ticketify.dto.CompleteTransactionDto;
import project.intro2se.ticketify.dto.CreateTransactionDto;
import project.intro2se.ticketify.service.PaypalService;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaypalController {
    private final PaypalService paypalService;
    @PostMapping(value = "/init")
    public CreateTransactionDto createPayment(
            @RequestBody BookingRequest bookingRequest ) throws IOException {
        return paypalService.createTransaction(bookingRequest);
    }

    @PostMapping(value = "/capture")
    public CompleteTransactionDto completePayment(@RequestParam("token") String token,
                                                  @RequestBody BookingRequest bookingRequest,
                                                  @AuthenticationPrincipal User user) {
        return paypalService.completeTransaction(token, bookingRequest, user);
    }




}
