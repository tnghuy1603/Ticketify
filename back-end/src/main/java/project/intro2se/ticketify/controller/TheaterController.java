package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.intro2se.ticketify.domain.Theater;
import project.intro2se.ticketify.service.TheaterService;

import java.util.List;

@RestController
@RequestMapping("/theaters")
@RequiredArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;
    @GetMapping
    public ResponseEntity<List<Theater>> findAll(){
        return ResponseEntity.ok(theaterService.findAll());
    }
}
