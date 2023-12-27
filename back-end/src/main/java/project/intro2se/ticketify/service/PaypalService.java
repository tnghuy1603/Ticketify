package project.intro2se.ticketify.service;


import com.google.zxing.WriterException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Transaction;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.BookingRequest;
import project.intro2se.ticketify.dto.CreateTransactionSession;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaypalService {
    private final TransactionService transactionService;
    private final PayPalHttpClient payPalHttpClient;
    @Transactional
    public CreateTransactionSession createTransaction(BigDecimal fee) throws IOException {
        MonetaryAmount feeInVnd = Money.of(fee, "VND");

//        CurrencyConversion vndToUsdConvertor = MonetaryConversions.getConversion("USD");
//        MonetaryAmount feeInUsd = feeInVnd.with(vndToUsdConvertor);
//        CurrencyUnit usdCurrency = Monetary.getCurrency("USD");
//        CurrencyUnit vndCurrency = Monetary.getCurrency("VND");
//        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider("ECB");
//
//        CurrencyConversion vndToUsdConvertor = exchangeRateProvider.getCurrencyConversion("USD");
//
//        MonetaryAmount feeInUsd = feeInVnd.with(vndToUsdConvertor);
//
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
//        log.info(feeInUsd.getNumber().toString());
//        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency("USD")
//                .setNumber(1).create();
//        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
//
//
//        CurrencyConversion conversionEUR = exchangeRateProvider.getCurrencyConversion("EUR");
//
//
//
//        MonetaryAmount convertedAmountUSDtoEUR = oneDollar.with(conversionEUR);
        BigDecimal feeInUsd = fee.divide(BigDecimal.valueOf(23000), 2, RoundingMode.HALF_UP);
//        BigDecimal rounded = feeInUsd.setScale(3, RoundingMode.HALF_UP);
        log.info(feeInUsd.toString());
        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown().currencyCode("USD").value(feeInUsd.toString());
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().amountWithBreakdown(amountBreakdown);
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:5173/payments/success")
                .cancelUrl("http://localhost:5173/payments/cancel");
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

            return new CreateTransactionSession("Success",  order.id(), redirectUrl);
        } catch (IOException e) {
            log.error(e.getMessage());
            CreateTransactionSession response =  new CreateTransactionSession();
            response.setStatus("error");
            return response;
        }
    }
    public Transaction completeTransaction(String token, BookingRequest request, User user) throws MessagingException, WriterException {
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
