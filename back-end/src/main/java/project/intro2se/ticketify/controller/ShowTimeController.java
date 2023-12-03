package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.dto.ScheduleByTheaterAndDate;
import project.intro2se.ticketify.dto.ScheduleByTheaterDto;
import project.intro2se.ticketify.dto.ScheduleTodayDto;
import project.intro2se.ticketify.service.ShowTimeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("showtimes")
@RequiredArgsConstructor
public class ShowTimeController {
    private final ShowTimeService showTimeService;
//    @GetMapping("")
//    public ResponseEntity<List<ShowTime>> findShowTimeByMovie(@RequestParam(name = "movie") Long movieId){
//        return ResponseEntity.ok(showTimeService.findByMovieId(movieId));
//    }
//    @GetMapping("/available-show-time")
//    public ResponseEntity<List<ShowTime>> findAvailableShowTime(@RequestParam(name = "movie") Long movieId){
//        return ResponseEntity.ok(showTimeService.findAvailableByMovie(movieId));
//    }
//    @GetMapping("/public")
//    public ResponseEntity<ScheduleByTheaterDto> findAvailableShowTimeByTheater(@RequestParam(name = "theater") Long theaterId){
//        return ResponseEntity.ok(showTimeService.findAvailableByTheater(theaterId));
//    }
    @PostMapping
    public ResponseEntity<Long> addShowTime(@RequestBody ShowTime showTime){
        return ResponseEntity.ok(showTimeService.addShowTime(showTime).getId());
    }
//    @GetMapping("/")
//    public ResponseEntity<?> findShowTimes(@RequestParam(name = "theater", required = false) Long theaterId,
//                                           @RequestParam(name = "room", required = false) Long roomId,
//                                           @RequestParam(name = "movie", required = false) Long movieId,
//                                           @RequestParam(name = "available", required = false) boolean isAvailable){
//        if(theaterId != null && isAvailable){
//            return ResponseEntity.ok(showTimeService.findAvailableByTheater(theaterId));
//        }
//        if(roomId != null && movieId == null){
//            return ResponseEntity.ok(showTimeService.findByRoom(roomId));
//        }
//        return ResponseEntity.ok("Something went wrong");
//
//
//    }
//    @GetMapping("/round-data")
//    public ResponseEntity<?> roundDateTime(){
//        showTimeService.roundToNearestQuaterHour();
//        return ResponseEntity.ok("Done");
//    }
//    @GetMapping
//    public ResponseEntity<List<ShowTime>> getAvailableShowTime(@RequestParam("movie") Long movieId,
//                                                     @RequestParam("theater") Long theaterId){
//        return ResponseEntity.ok(showTimeService.findAvailableByTheaterAndMovie(theaterId, movieId));
//    }
//    @GetMapping
//    public ResponseEntity<List<ShowTime>> getByDateAndTheater(@RequestParam("movie") Long movieId,
//                                                                @RequestParam("theater") Long theaterId){
//        return ResponseEntity.ok(showTimeService.findAvailableByTheaterAndMovie(theaterId, movieId));
//
//    }
@GetMapping
public ResponseEntity<ScheduleByTheaterAndDate> getByDateAndTheater(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                          @RequestParam("theater") Long theaterId){
    return ResponseEntity.ok(showTimeService.getShowTimeByTheaterAndDate(date, theaterId));

}





}
