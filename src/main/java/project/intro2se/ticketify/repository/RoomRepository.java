package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.intro2se.ticketify.domain.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByTheater_Id(Long theaterId);
}
