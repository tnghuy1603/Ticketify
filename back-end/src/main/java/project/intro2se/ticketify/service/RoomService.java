package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Room;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.RoomRepository;
import project.intro2se.ticketify.repository.ShowTimeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ShowTimeRepository showTimeRepository;
    public boolean isAvailableRoom(Room room, LocalDateTime startAt, LocalDateTime endAt){
        List<ShowTime> showTimesOfToday = showTimeRepository.findShowTimeTodayByRoom(room.getId());
        for(ShowTime showTime: showTimesOfToday){
            if((showTime.getStartAt().isAfter(startAt) && showTime.getStartAt().isBefore(endAt) )
                    || (showTime.getEndAt().isAfter(startAt) && showTime.getEndAt().isBefore(endAt)))
            {
                return false;
            }
        }
        return true;
    }
    public List<Room> findByTheater(Long theaterId){
        return roomRepository.findByTheater_Id(theaterId);
    }
    public Room findById(Long roomId){
        return roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No room with that id"));
    }


}
