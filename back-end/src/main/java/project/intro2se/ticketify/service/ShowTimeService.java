package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.domain.Room;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.domain.Ticket;
import project.intro2se.ticketify.dto.*;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.MovieRepository;
import project.intro2se.ticketify.repository.RoomRepository;
import project.intro2se.ticketify.repository.ShowTimeRepository;
import project.intro2se.ticketify.repository.TicketRepository;

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
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;

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

    public ScheduleByTheaterAndDate findShowTimeByTheaterAndDate(LocalDate date, Long theaterId){
        List<ShowTime> showTimes = showTimeRepository.findByTheaterAndDate(theaterId, date);
        Set<Movie> movies = new HashSet<>();
        for(ShowTime showTime: showTimes){
            movies.add(showTime.getMovie());
        }
        return new ScheduleByTheaterAndDate(date, movies, showTimes);
    }


    public List<ShowTime> findAvailableByTheaterAndMovie(Long theaterId, Long movieId){
        return showTimeRepository.findAvailableByTheaterAndMovie(theaterId, movieId);
    }

    public List<ShowTime> findAvailableByRoom(Long roomId){
        return showTimeRepository.findByRoom_IdAndStartAtAfter(roomId, LocalDateTime.now());
    }

    public List<ShowTime> findByRoom(Long roomId){
        return showTimeRepository.findByRoom_Id(roomId);
    }

    public List<ShowTime> findByMovieId(Long movieId){
        return showTimeRepository.findByMovie_Id(movieId);
    }

    public List<ShowTime> findAvailableByMovie(Long movieId){
        return showTimeRepository.findByMovie_IdAndStartAtAfter(movieId, LocalDateTime.now());
    }

    public ShowTime addShowTime(AddShowTimeRequest request){
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("No room with that id"));
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("No movie with that id"));
        ShowTime showTime = ShowTime.builder()
                .room(room)
                .movie(movie)
                .endAt(LocalDateTime.parse(request.getEndAt()))
                .startAt(LocalDateTime.parse(request.getStartAt()))
                .build();

        return showTimeRepository.save(showTime);
    }
    // Can only update showTime if there are not any booked ticket yet.
    public ShowTime updateShowTime(UpdateShowTimeRequest updateShowTimeRequest){
        ShowTime showTimeToUpdate = showTimeRepository.findById(updateShowTimeRequest.getShowTimeId())
                .orElseThrow(() -> new ResourceNotFoundException("No showtime with that id"));
        List<Ticket> tickets = ticketRepository.findByShowTimeAndBooked(showTimeToUpdate, true);
        if(!tickets.isEmpty()){
            throw new IllegalArgumentException("Can not update show time if there are any booked ticket");

        }
        Movie movie = movieRepository.findById(updateShowTimeRequest.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("No such movie with that id"));
        Room room = roomRepository.findById(updateShowTimeRequest.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("No such room with that id"));
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

    public TodaySchedule findByToday(Long theaterId){
        List<ShowTime> showTimes = showTimeRepository.findByTheaterAndDate(theaterId, LocalDate.now());
        Set<Movie> movies = new HashSet<>();
        return new TodaySchedule(movies, showTimes);
    }


    public CustomResponse deleteShowTime(Long showTimeId) {
        if(!showTimeRepository.existsById(showTimeId)){
            throw new ResourceNotFoundException("Not found any showtime with id = " + showTimeId);
        }
        if(ticketRepository.existsTicketByBookedAndShowTime_Id(true, showTimeId)){
            return new CustomResponse("Some tickets of movies are booked. Can not delete this showtime", null, LocalDateTime.now());
        }
        showTimeRepository.deleteById(showTimeId);
        return new CustomResponse("Delete showtime successfully", null, LocalDateTime.now());
    }
}
