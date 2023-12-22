package project.intro2se.ticketify.controller;


import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.Transaction;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.BookingRequest;
import project.intro2se.ticketify.dto.CreateTransactionSession;
import project.intro2se.ticketify.service.PaypalService;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("checkout")
@RequiredArgsConstructor
public class PaypalController {
    private final PaypalService paypalService;
//    @PostMapping(value = "/init")
//    public CreateTransactionDto createPayment(
//            @RequestBody BookingRequest bookingRequest ) throws IOException {
//        return paypalService.createTransaction(bookingRequest);
//    }
@PostMapping(value = "/init")
public ResponseEntity<CreateTransactionSession> createPayment(
        @RequestParam @NotNull BigDecimal fee) throws IOException {
    return ResponseEntity.ok(paypalService.createTransaction(fee));
}


@PostMapping(value = "/capture")
public ResponseEntity<Transaction> completePayment(@RequestParam("token") @NotBlank(message = "Token must not be null") String token,
                                                   @Valid  @RequestBody BookingRequest request, @AuthenticationPrincipal User user) throws WriterException, MessagingException {
    return ResponseEntity.ok(paypalService.completeTransaction(token, request, user));
}





}
