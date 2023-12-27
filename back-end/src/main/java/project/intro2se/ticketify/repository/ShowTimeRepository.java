package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.intro2se.ticketify.domain.ShowTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findByMovie_Id(Long movieId);
    List<ShowTime> findByMovie_IdAndStartAtAfter(Long movieId, LocalDateTime startAt);

    @Query("SELECT st FROM ShowTime st JOIN st.room r WHERE r.theater.id = :theaterId AND DATE(st.startAt) = :date")
    List<ShowTime> findByTheaterAndDate(@Param("theaterId") Long theaterId, @Param("date")LocalDate date);
    @Query("SELECT st FROM ShowTime st JOIN st.room r WHERE r.theater.id = :theaterId" +
            " AND st.startAt >= CURRENT DATE AND st.movie.id = :movieId")
    List<ShowTime> findAvailableByTheaterAndMovie(@Param("theaterId") Long theaterId, @Param("movieId") Long movieId);

    List<ShowTime> findByRoom_IdAndStartAtAfter(Long roomId, LocalDateTime startAt);

    @Query("SELECT st From ShowTime st JOIN st.room r WHERE r.id = :roomId AND DATE(st.startAt) = :date ")
    List<ShowTime> findByDateAndRoom(@Param("date") LocalDate date, @Param("roomId") Long roomId);

    List<ShowTime> findByRoom_Id(Long roomId);
    @Query("SELECT st FROM ShowTime st JOIN st.room r WHERE r.theater.id = :theaterId")
    List<ShowTime> findByTheater(@Param("theaterId") Long theaterId);

    boolean existsShowTimeByMovie_Id(Long movieId);
}
