package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.Room;
import project.intro2se.ticketify.service.RoomService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;
    @GetMapping
    public ResponseEntity<?> findRoom(@RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
                                      @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt,
                                      @RequestParam(name = "theater", required = false) Long theaterId){
        if(startAt != null && endAt != null && theaterId == null){
            return ResponseEntity.ok(roomService.findUnoccupiedRoom(startAt, endAt));
        }
        if(startAt == null && endAt == null && theaterId != null){
            return ResponseEntity.ok(roomService.findByTheater(theaterId));
        }
        return ResponseEntity.ok(roomService.findAll());
    }
    @GetMapping("/available")
    public ResponseEntity<?> isAvailable(@RequestParam(name = "room") Long roomId,
                                         @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startAt,
                                         @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt){
        return ResponseEntity.ok(roomService.isAvailableRoom(roomId, startAt, endAt));
    }

}
