package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.domain.Room;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.dto.ScheduleByTheaterDto;
import project.intro2se.ticketify.dto.ScheduleToday;
import project.intro2se.ticketify.dto.UpdateShowTimeRequest;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.MovieRepository;
import project.intro2se.ticketify.repository.ShowTimeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowTimeService {
    private final ShowTimeRepository showTimeRepository;
    private final MovieRepository movieRepository;
    private final RoomService roomService;

    //Schedule of theater viewed by moviegoer or guest
    public ScheduleByTheaterDto findAvailableByTheater(Long theaterId){
        List<ShowTime> showTimes =  showTimeRepository.findAvailableByTheater(theaterId);
        Set<Movie> movies = new HashSet<>();
        for(ShowTime showTime: showTimes){
            movies.add(showTime.getMovie());
        }
        return new ScheduleByTheaterDto(movies, showTimes);
    }
    //Schedule of individual movie viewed by moviegoer and guest
    public List<ShowTime> findAvailableByTheaterAndMovie(Long theaterId, Long movieId){
        return showTimeRepository.findAvailableByTheaterAndMovie(theaterId, movieId);
    }

    public List<ShowTime> findAvailableByRoom(Long roomId){
        return showTimeRepository.findByRoom_IdAndStartAtAfter(roomId, LocalDateTime.now());
    }
    // Ticket manager must choose room first
    public List<ShowTime> findByRoom(Long roomId){
        return showTimeRepository.findByRoom_Id(roomId);
    }
    public ShowTime addShowTime(ShowTime showTime){
        //Check if the room is available or not
        if(!roomService.isAvailableRoom(showTime.getRoom(), showTime.getStartAt(), showTime.getEndAt())){
            throw new IllegalArgumentException("Room is not available");
        }
        return showTimeRepository.save(showTime);
    }
    // Can only update showTime if there are not any booked ticket yet.
    public ShowTime updateShowTime(UpdateShowTimeRequest updateShowTimeRequest){
        Room room = roomService.findById(updateShowTimeRequest.getRoomId());
        if(roomService.isAvailableRoom(room, updateShowTimeRequest.getStartAt(), updateShowTimeRequest.getEndAt())){
            throw new IllegalArgumentException("Room is busy");
        }
        ShowTime showTimeToUpdate = showTimeRepository.findById(updateShowTimeRequest.getShowTimeId())
                .orElseThrow(() -> new ResourceNotFoundException(""));
        Movie movie = movieRepository.findById(updateShowTimeRequest.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("No such id"));
        showTimeToUpdate.setRoom(room);
        showTimeToUpdate.setMovie(movie);
        showTimeToUpdate.setStartAt(updateShowTimeRequest.getStartAt());
        showTimeToUpdate.setEndAt(updateShowTimeRequest.getEndAt());
        return showTimeRepository.save(showTimeToUpdate);
    }
    //Ticket manager must choose theater first
    public List<ShowTime> findByTheater(Long theaterId){
        return showTimeRepository.findByTheater(theaterId);
    }
    //Schedule viewed by receptionist
    public ScheduleToday findByToday(Long theaterId){
        List<ShowTime> showTimes = showTimeRepository.findByTheaterAndDay(theaterId, LocalDate.now());
        Set<Movie> movies = new HashSet<>();
        return new ScheduleToday(movies, showTimes);
    }
    public List<ShowTime> findByMovieId(Long movieId){
        return showTimeRepository.findByMovie_Id(movieId);
    }
    public List<ShowTime> findAvailableByMovie(Long movieId){
        return showTimeRepository.findByMovie_IdAndStartAtAfter(movieId, LocalDateTime.now());
    }





}
