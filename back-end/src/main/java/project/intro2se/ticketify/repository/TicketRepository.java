package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.intro2se.ticketify.domain.Ticket;

import java.util.List;
import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByShowTime_Id(Long showTimeId);
    @Query("select COUNT(t) From Ticket t WHERE t.showTime.id = :showTimeId")
    int soldTicketByShowTimeId(Long showTimeId);

}