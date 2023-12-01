package project.intro2se.ticketify.service;


import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
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

    public CreateTransactionDto createTransaction(BookingRequest request) throws IOException {

        BigDecimal fee = transactionService.getTotalAmount(request);
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



            return new CreateTransactionDto("success",  order.id(), redirectUrl);
        } catch (IOException e) {
            log.error(e.getMessage());
            CreateTransactionDto response =  new CreateTransactionDto();
            response.setStatus("error");
            return response;
        }
    }
    public CompleteTransactionDto completeTransaction(String token ,BookingRequest request,  User user){
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                Transaction transaction = new Transaction();
                transaction.setId(token);
                transaction.setUser(user);
                AmountWithBreakdown amount = httpResponse.result().purchaseUnits().get(0).amountWithBreakdown();
                BigDecimal totalAmount = BigDecimal.valueOf(Long.parseLong(amount.value()));
                transaction.setTotal(totalAmount);
                transaction.setCreatedAt(LocalDateTime.now());
                transactionService.saveOne(transaction);

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        CompleteTransactionDto completeTransactionDto = new CompleteTransactionDto();
        completeTransactionDto.setMessage("error");
        return completeTransactionDto;
    }

}
