package project.intro2se.ticketify.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.dto.AddMovieRequest;
import project.intro2se.ticketify.dto.UpdateMovieRequest;
import project.intro2se.ticketify.service.MovieService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @GetMapping
    public ResponseEntity<List<Movie>> findByStatus(@RequestParam(name = "status") @NotBlank String status){
        return ResponseEntity.ok(movieService.findByStatus(status));
    }
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Movie> updateMovie(@RequestPart(name = "poster", required = false) MultipartFile file, @RequestPart(name = "movie") UpdateMovieRequest request) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(movieService.update(request, file));
    }
    @GetMapping("manager")

    public ResponseEntity<List<Movie>> findAll(){
        return ResponseEntity.ok(movieService.findAll());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Movie> addMovie(@RequestPart(name = "movie") AddMovieRequest request, @RequestPart("poster") MultipartFile poster) throws ExecutionException, InterruptedException {

        return ResponseEntity.ok(movieService.add(request, poster));
    }
    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(movieService.deleteById(movieId));
    }








}
