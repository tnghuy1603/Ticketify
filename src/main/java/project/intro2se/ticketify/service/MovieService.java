package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.MovieRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    // moviegoer
    public Movie findById(Long movieId){
        return movieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("No movie with that id"));
    }
    public List<Movie> findByStatus(String status){
        return movieRepository.findByStatus(status);
    }

    //ticket manager
    public Movie update(Movie movie){
        Movie movieToUpdate = movieRepository.findById(movie.getId()).orElseThrow();

        return movieRepository.save(movie);
    }
    public Movie add(Movie movie){
        return movieRepository.save(movie);
    }
    public List<Movie> findAll(){
        return movieRepository.findAll();
    }


}
