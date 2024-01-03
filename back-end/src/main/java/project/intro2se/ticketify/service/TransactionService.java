package project.intro2se.ticketify.service;

import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import project.intro2se.ticketify.domain.*;
import project.intro2se.ticketify.dto.*;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.FoodOrderLineRepository;
import project.intro2se.ticketify.repository.FoodRepository;
import project.intro2se.ticketify.repository.TicketRepository;
import project.intro2se.ticketify.repository.TransactionRepository;
import project.intro2se.ticketify.utils.QRCodeGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableTransactionManagement

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TicketRepository ticketRepository;
    private final MailService mailService;
    private final FoodRepository foodRepository;
    private final FoodOrderLineRepository foodOrderLineRepository;
//    private final PaypalService paypalService;

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
    public DailyRevenue calculateDailyRevenue(LocalDate date){
        List<Transaction> transactions = transactionRepository.findByDate(date);
        BigDecimal revenueOfDay = BigDecimal.ZERO;
        for(Transaction transaction: transactions){
            revenueOfDay = revenueOfDay.add(transaction.getTotal());
        }
        return new DailyRevenue(date, revenueOfDay);
    }
    public MonthlyRevenue calculateMonthlyRevenue(YearMonth yearMonth){
        List<DailyRevenue> dailyRevenues = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for(int i = 1; i <= yearMonth.lengthOfMonth(); i++){
            LocalDate date = yearMonth.atDay(i);
            DailyRevenue dailyRevenue = calculateDailyRevenue(date);
            total = total.add(dailyRevenue.getTotalRevenue());
            dailyRevenues.add(dailyRevenue);
        }

        return new MonthlyRevenue(yearMonth, dailyRevenues, total);
    }


    public ConfirmBookingResponse confirmBooking(BookingRequest request){
        Transaction transaction = new Transaction();
        BigDecimal totalPrice = getTotalAmount(request, transaction);
        return new ConfirmBookingResponse(transaction.getTickets(), totalPrice, transaction.getFoodOrderLines());
    }
    public Transaction saveCompletedTransaction(String transactionId, BookingRequest request, User user) throws MessagingException, IOException, WriterException {
        Transaction transaction = new Transaction();
        BigDecimal totalPrice = getTotalAmount(request, transaction);
        List<Ticket> tickets = transaction.getTickets();
        for(Ticket ticket: tickets){
            ticket.setBooked(true);
        }
        transaction.setId(transactionId);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTotal(totalPrice);
        transaction.setUser(user);
        byte[] qrCode = QRCodeGenerator.getQRCodeImage(new TransactionDto());
        //Send an email with qrcode
        mailService.sendEmailWithEmbeddedImages(user.getDisplayName(), user.getEmail(), "Confirm booking",
                "Thank for choosing us", qrCode );
        return transactionRepository.save(transaction);
    }



    @Transactional()
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

        totalPrice = ticketPrice.add(foodPrice);

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

    public BigDecimal getTotalFoodPrice(List<FoodOrderLineDto> orderLineDtoList, Transaction transaction) {
        BigDecimal foodPrice = BigDecimal.ZERO;
        if(orderLineDtoList == null){
            return foodPrice;
        }
        List<FoodOrderLine> foodOrderLines = new ArrayList<>();
        for(FoodOrderLineDto orderLineDto: orderLineDtoList){
            Food food = foodRepository.findById(orderLineDto.getFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException("No food with that id"));
            BigDecimal price = food.getPrice().multiply(BigDecimal.valueOf(orderLineDto.getQuantity()));
            FoodOrderLine foodOrderLine = new FoodOrderLine();
            foodOrderLine.setFood(food);
            foodOrderLine.setQuantity(orderLineDto.getQuantity());
            foodPrice = foodPrice.add(price);
            foodOrderLines.add(foodOrderLine);
        }
        transaction.setFoodOrderLines(foodOrderLines);
        return foodPrice;
    }


    private BigDecimal getTotalTicketPrice(List<Long> ticketIds, Transaction transaction){
        BigDecimal ticketPrice = BigDecimal.ZERO;
        List<Ticket> tickets = new ArrayList<>();
        for(Long ticketId: ticketIds){
            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                    () -> new ResourceNotFoundException("Not found ticket with that id"));
            ticket.setTransaction(transaction);
            ticketPrice = ticketPrice.add(ticket.getPrice());
            tickets.add(ticket);
        }
        transaction.setTickets(tickets);
        return ticketPrice;
    }

    public BigDecimal getTotalAmount(BookingRequest request, Transaction transaction){
        BigDecimal ticketPrice = getTotalTicketPrice(request.getTicketIds(), transaction);
        BigDecimal foodPrice = getTotalFoodPrice(request.getOrderLineDtoList(), transaction);
        return ticketPrice.add(foodPrice);
    }

    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }
    @Transactional
    public Transaction bookAtTicketCounter(BookingRequest request, User user){
        Transaction transaction = new Transaction();
        BigDecimal totalPrice = getTotalAmount(request, transaction);
        for(Ticket ticket: transaction.getTickets()){
            ticket.setBooked(true);
        }
//        transaction.setId("temp");
        transaction.setTotal(totalPrice);
        transaction.setUser(user);
        transaction.setCreatedAt(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }


    public List<Transaction> findAllByUser(User user) {
        return transactionRepository.findByUser(user);
    }
}
