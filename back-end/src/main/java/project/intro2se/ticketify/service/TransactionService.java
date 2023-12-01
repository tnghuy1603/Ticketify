package project.intro2se.ticketify.service;

import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.*;
import project.intro2se.ticketify.dto.BookingRequest;
import project.intro2se.ticketify.dto.CreateTransactionDto;
import project.intro2se.ticketify.dto.FoodOrderLineDto;
import project.intro2se.ticketify.dto.TransactionDto;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.FoodOrderLineRepository;
import project.intro2se.ticketify.repository.FoodRepository;
import project.intro2se.ticketify.repository.TicketRepository;
import project.intro2se.ticketify.repository.TransactionRepository;
import project.intro2se.ticketify.utils.QRCodeGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;
    private final MailService mailService;
    private final FoodRepository foodRepository;

//    private final PaypalService paypalService;
//    public List<TransactionDto> findByUser(User user){
//        List<Transaction> transactions =  transactionRepository.findByUser(user);
//        return convertToDtoList(transactions, user);
//    }
//    public List<Transaction> findByMovie(Long movieId){
//        return transactionRepository.findByMovieId(movieId);
//    }
//    public TransactionDto findById(String transactionId, User user){
//        Transaction transaction =  transactionRepository.findById(transactionId)
//                .orElseThrow(() -> new ResourceNotFoundException("No found any transaction with that id"));
//        return convertToDto(transaction, user);
//    }
//    private TransactionDto convertToDto(Transaction transaction, User user){
//        List<Ticket> tickets = ticketRepository.findByTransaction(transaction);
//        StringBuilder seats = new StringBuilder();
//        BigDecimal ticketPrice = BigDecimal.ONE;
//        for(Ticket ticket: tickets){
//            seats.append(ticket.getSeat().getSeatNumber()).append(", ");
//            ticketPrice = ticketPrice.add(ticket.getPrice());
//        }
//        ShowTime showTime = tickets.iterator().next().getShowTime();
//        Room room = showTime.getRoom();
//        Movie movie = showTime.getMovie();
//        Theater theater = room.getTheater();
//
//        return TransactionDto.builder()
//                .theaterAddress(theater.getAddress())
//                .theaterName(theater.getName())
//                .showRoom(room.getId())
//                .movieTitle(movie.getTitle())
//                .startTime(showTime.getStartAt())
//                .createdAt(transaction.getCreatedAt())
//                .username(user.getUsername())
//                .totalPrice(transaction.getTotal())
//                .seats(seats.toString())
//                .ticketPrice(ticketPrice)
//                .foodPrice(transaction.getTotal().subtract(ticketPrice))
//                .build();
//    }
//    public List<TransactionDto> convertToDtoList(List<Transaction> transactions, User user){
//        List<TransactionDto> res = new ArrayList<>();
//        for(Transaction transaction: transactions){
//            res.add(convertToDto(transaction, user));
//        }
//        return res;
//    }
//    public CreateTransactionDto creatTransaction(BookingRequest request) throws IOException {
//        Transaction transaction = new Transaction();
//        BigDecimal totalPrice;
//        BigDecimal ticketPrice = BigDecimal.ZERO;
//        BigDecimal foodPrice = BigDecimal.ZERO;
//        List<Ticket> tickets = new ArrayList<>();
//        for(Long ticketId: request.getTicketIds()){
//            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
//                    () -> new ResourceNotFoundException("Not found tickets"));
//            ticketPrice = ticketPrice.add(ticket.getPrice());
//            tickets.add(ticket);
//        }
//        for(long orderLineId: request.getFoodOrderLineIds()){
//            FoodOrderLine foodOrderLine = foodOrderLineRepository.findById(orderLineId).orElseThrow(
//                    () -> new ResourceNotFoundException("Not found order line"));
//            BigDecimal price = BigDecimal.valueOf(foodOrderLine.getQuantity())
//                    .multiply(foodOrderLine.getFood().getPrice());
//            foodPrice = foodPrice.add(price);
//        }
//        totalPrice = ticketPrice.add(foodPrice);
//        return paypalService.createTransaction(totalPrice);
//    }

    public Transaction bookTickets(BookingRequest request, User user) throws IOException, WriterException, MessagingException {
        Transaction transaction = new Transaction();
        BigDecimal totalPrice;
        BigDecimal ticketPrice = BigDecimal.ZERO;
        BigDecimal foodPrice = BigDecimal.ZERO;
        List<Ticket> tickets = new ArrayList<>();
        for(Long ticketId: request.getTicketIds()){
            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                    () -> new ResourceNotFoundException("Not found tickets"));
            ticket.setBooked(true);
            ticket.setTransaction(transaction);
            ticketPrice = ticketPrice.add(ticket.getPrice());
            tickets.add(ticket);
        }

        totalPrice = getTotalAmount(request);
        transaction.setTotal(totalPrice);
        transaction.setTickets(tickets);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUser(user);
        log.info("Saving tickets and transaction");
        transaction = transactionRepository.save(transaction);
        TransactionDto transactionDto = new TransactionDto();
        byte[] qrCode = QRCodeGenerator.getQRCodeImage(new TransactionDto());
        //Send an email with qrcode
        mailService.sendEmailWithEmbeddedImages(user.getDisplayName(), user.getEmail(), "Confirm booking",
                "Thank for choosing us", qrCode );
        return transaction;
    }

    private BigDecimal getTotalFoodPrice(List<FoodOrderLineDto> orderLineDtoList) {
        BigDecimal foodPrice = BigDecimal.ZERO;
        for(FoodOrderLineDto orderLineDto: orderLineDtoList){
            Food food = foodRepository.findById(orderLineDto.getFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException("No food with that id"));
            BigDecimal price = food.getPrice().multiply(BigDecimal.valueOf(orderLineDto.getQuantity()));
            foodPrice = foodPrice.add(price);
        }
        return foodPrice;
    }
    public Transaction saveSuccessTransaction(String transactionId, BigDecimal totalAmount, BookingRequest request,  User user){
        Transaction transaction = new Transaction();
        transaction.setId(transaction.getId());
        transaction.setUser(user);
        transaction.setTotal(totalAmount);
        List<Ticket> tickets = new ArrayList<>();
        for(Long ticketId: request.getTicketIds()){
            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                    () -> new ResourceNotFoundException("Not found ticket with that id"));
            ticket.setTransaction(transaction);
            ticket.setBooked(true);
            tickets.add(ticket);
        }
        transaction.setTickets(tickets);
        List<FoodOrderLine> foodOrderLines = new ArrayList<>();
        for(FoodOrderLineDto orderLineDto: request.getOrderLineDtoList()){
            Food food = foodRepository.findById(orderLineDto.getFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException("No food with that id"));
            FoodOrderLine foodOrderLine = FoodOrderLine.builder()
                    .food(food)
                    .quantity(orderLineDto.getQuantity())
                    .transaction(transaction)
                    .build();
        }
        transaction.setFoodOrderLines(foodOrderLines);
        return transaction;


    }
    private BigDecimal getTotalTicketPrice(List<Long> ticketIds){
        BigDecimal ticketPrice = BigDecimal.ZERO;
        for(Long ticketId: ticketIds){
            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                    () -> new ResourceNotFoundException("Not found ticket with that id"));
            ticketPrice = ticketPrice.add(ticket.getPrice());
        }
        return ticketPrice;
    }

    public BigDecimal getTotalAmount(BookingRequest request){
        BigDecimal ticketPrice = getTotalTicketPrice(request.getTicketIds());
        BigDecimal foodPrice = getTotalFoodPrice(request.getOrderLineDtoList());
        return ticketPrice.add(foodPrice);
    }
    public Transaction saveOne(Transaction transaction){
        return transactionRepository.save(transaction);
    }







}
