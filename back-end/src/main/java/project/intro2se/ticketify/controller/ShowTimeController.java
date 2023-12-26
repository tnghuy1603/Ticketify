package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.ShowTime;
import project.intro2se.ticketify.dto.AddShowTimeRequest;
import project.intro2se.ticketify.service.ShowTimeService;

import java.time.LocalDate;

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
    public ResponseEntity<ShowTime> addShowTime(@RequestBody AddShowTimeRequest request){
        return ResponseEntity.ok(showTimeService.addShowTime(request));
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
    @GetMapping
    public ResponseEntity<?> findShowTime(@RequestParam(name = "movie", required = false) Long movieId,
                                                              @RequestParam("theater") Long theaterId,
                                                              @RequestParam(name = "date", required = false)
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        if(movieId != null){
            return ResponseEntity.ok(showTimeService.findAvailableByTheaterAndMovie(theaterId, movieId));
        }
        return ResponseEntity.ok(showTimeService.findShowTimeByTheaterAndDate(date, theaterId));
    }
    @DeleteMapping("/{showTimeId}")
    public ResponseEntity<?> deleteShowTime(@PathVariable Long showTimeId){
        return ResponseEntity.ok(showTimeService.deleteShowTime(showTimeId));
    }








}
