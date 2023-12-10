package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import project.intro2se.ticketify.domain.Transaction;
import project.intro2se.ticketify.domain.User;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUser(User user);
    @Query("SELECT t FROM Transaction t JOIN t.tickets tk JOIN tk.showTime st WHERE st.movie.id = :movieId")
    List<Transaction> findByMovieId(@Param("movieId") Long movieId);
    @Query("SELECT t FROM Transaction t WHERE MONTH(t.createdAt) = MONTH(:month) AND YEAR(t.createdAt) = YEAR(:month)")
    List<Transaction> findAllInMonth(@Param("month") YearMonth month);
    @Query("SELECT t FROM Transaction t WHERE t.createdAt = CURRENT_DATE")
    List<Transaction> findByCurrentDate();
    @Query("SELECT t FROM Transaction t WHERE DATE(t.createdAt) = :date")
    List<Transaction> findByDate(@Param("date") LocalDate date);



}
