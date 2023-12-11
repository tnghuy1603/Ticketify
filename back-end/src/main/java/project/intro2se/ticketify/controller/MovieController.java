package project.intro2se.ticketify.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.service.MovieService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("")
    public ResponseEntity<List<Movie>> findMovies(@RequestParam(name = "status", required = false) @NotBlank String status){
        if(status != null){
            return ResponseEntity.ok(movieService.findByStatus(status));
        } else{
            return ResponseEntity.ok(movieService.findAll());
        }

    }
    @PutMapping("")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie){
        return ResponseEntity.ok(movieService.update(movie));
    }

//    @PostMapping("")
//    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
//        return ResponseEntity.ok(movieService)
//    }







}
