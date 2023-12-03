package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.domain.Room;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.dto.*;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.MovieRepository;
import project.intro2se.ticketify.repository.ShowTimeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowTimeService {
    private final ShowTimeRepository showTimeRepository;
    private final MovieRepository movieRepository;
    private final RoomService roomService;

    public List<ShowTime> roundToNearestQuaterHour(){
        List<ShowTime> showTimes = showTimeRepository.findAll();

        for(ShowTime showTime: showTimes){
//            LocalDateTime startAt = showTime.getStartAt();
//            log.info("Movie");
//            showTime.setStartAt(startAt.truncatedTo(ChronoUnit.HOURS).plusMinutes(15 * (startAt.getMinute() / 15)));
            Movie movie = showTime.getMovie();
            int duration = movie.getDuration();
            showTime.setEndAt(showTime.getStartAt().plusMinutes(duration));
        }
        log.info("Initial size: " + showTimes.size());

//        Room room = new Room();
//        Room room1 = new Room();
//        Movie movie = new Movie();
//        LocalDateTime current = LocalDateTime.now();
//        ShowTime showTime = new ShowTime();
//        ShowTime showTime1 = new ShowTime();
//        showTime.setStartAt(current);
//        showTime.setRoom(room);
//        showTime.setMovie(movie);
//        showTime1.setStartAt(current);
//        showTime1.setRoom(room);
//        showTime1.setMovie(movie);
//        log.info("2 showtime is: " +  showTime1.equals(showTime));
//        Set<ShowTime> showTimeSet = new HashSet<>();
//        for(ShowTime showTime2: showTimes) {
//            addDistinct(showTimeSet, showTime2);
//        }
//        log.info("Size after remove duplicate" + showTimeSet.size());
//        showTimeRepository.deleteAll();
        //Remove invalid showtime


//        return showTimes;
        return showTimeRepository.saveAll(showTimes);
    }
    private void addDistinct(Set<ShowTime> showTimeSet, ShowTime showTime){
        for(ShowTime showTime1: showTimeSet) {
            if (showTime1.getMovie().getId().equals(showTime.getMovie().getId()) &&
                    showTime1.getRoom().getId().equals(showTime.getRoom().getId()) &&
                    showTime1.getStartAt().equals(showTime.getStartAt())) {
                log.info("Showtime is equal");
                return;
            }
        }
        showTimeSet.add(showTime);
        log.info("Size of showtime" + showTimeSet.size());
    }
    private boolean isOccupiedRoom(Set<ShowTime> showTimeSet, ShowTime showTime){
        for(ShowTime showTime1: showTimeSet) {
//            if((showTime1.getStartAt().isAfter(showTime1.getStartAt()) && showTime1.getStartAt().isBefore(showTime.getEndAt()) )
//                    || (showTime1.getEndAt().isAfter(startAt) && showTime1.getEndAt().isBefore(endAt)))
//            {
//                return false;
//            }
        }
        return false;
    }
    public ScheduleByTheaterAndDate getShowTimeByTheaterAndDate(LocalDate date, Long theaterId){
        log.info("Hit service layer");
        List<ShowTime> showTimes = showTimeRepository.findByTheaterAndDate(theaterId, date);

        return new ScheduleByTheaterAndDate(date, null, showTimes);
    }
    public ScheduleByTheaterAndMovie getScheduleByTheaterAndMovie(Long movieId, Long theaterId){
        List<ShowTime> showTimes = showTimeRepository.findAvailableByTheaterAndMovie(movieId, theaterId);
        return new ScheduleByTheaterAndMovie(showTimes);
    }








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
    public ScheduleTodayDto findByToday(Long theaterId){
        List<ShowTime> showTimes = showTimeRepository.findByTheaterAndDate(theaterId, LocalDate.now());
        Set<Movie> movies = new HashSet<>();
        return new ScheduleTodayDto(movies, showTimes);
    }
    public List<ShowTime> findByMovieId(Long movieId){
        return showTimeRepository.findByMovie_Id(movieId);
    }
    public List<ShowTime> findAvailableByMovie(Long movieId){
        return showTimeRepository.findByMovie_IdAndStartAtAfter(movieId, LocalDateTime.now());
    }

}
