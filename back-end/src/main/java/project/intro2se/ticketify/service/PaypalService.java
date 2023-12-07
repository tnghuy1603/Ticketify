package project.intro2se.ticketify.service;


import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Transaction;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.BookingRequest;
import project.intro2se.ticketify.dto.CompleteTransactionDto;
import project.intro2se.ticketify.dto.CreateTransactionDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalService {
    private final TransactionService transactionService;
    private final PayPalHttpClient payPalHttpClient;
    @Transactional
    public CreateTransactionDto createTransaction(BigDecimal fee) throws IOException {

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown().currencyCode("USD").value(fee.toString());
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().amountWithBreakdown(amountBreakdown);
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:8080/payments/success")
                .cancelUrl("http://localhost:8080/payments/cancel");
        orderRequest.applicationContext(applicationContext);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);

        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();
            String redirectUrl = order.links().stream()
                    .filter(link -> "approve".equals(link.rel()))
                    .findFirst()
                    .orElseThrow(() ->new NoSuchElementException("Something went wrong"))
                    .href();

            return new CreateTransactionDto("Success",  order.id(), redirectUrl);
        } catch (IOException e) {
            log.error(e.getMessage());
            CreateTransactionDto response =  new CreateTransactionDto();
            response.setStatus("error");
            return response;
        }
    }
    public Transaction completeTransaction(String token, BookingRequest request, User user){
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                return transactionService.saveCompletedTransaction(token, request, user);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
