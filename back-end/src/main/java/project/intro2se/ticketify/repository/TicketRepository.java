package project.intro2se.ticketify.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.domain.Ticket;
import project.intro2se.ticketify.domain.Transaction;

import java.util.List;
import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByShowTime_Id(Long showTimeId);
    @Query("select COUNT(t) From Ticket t WHERE t.showTime.id = :showTimeId")
    int soldTicketByShowTimeId(Long showTimeId);
    List<Ticket> findByTransaction_Id(String transactionId);
    List<Ticket> findByTransaction(Transaction transaction);
    List<Ticket> findByShowTimeAndBooked(ShowTime showTime, boolean booked);

    boolean existsTicketByBookedAndShowTime_Id(boolean booked, Long showtimeId);
    @Modifying
    @Transactional
    int deleteByShowTime_Id(Long showtimeId);


}
