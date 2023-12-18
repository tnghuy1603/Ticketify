package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.dto.AddMovieRequest;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.MovieRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;
    private final CloudinaryService cloudinaryService;
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
    public Movie add(AddMovieRequest request, MultipartFile file) throws ExecutionException, InterruptedException {
        Map data = cloudinaryService.upload(file).get();
        String posterUrl = (String) data.get("url");
        log.info("Url: " + posterUrl);

        Movie movie = Movie.builder()
                .cast(request.getCast())
                .director(request.getDirector())
                .duration(request.getDuration())
                .openingDay(request.getOpeningDay())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .rated(request.getRated())
                .title(request.getTitle())
                .story(request.getStory())
                .trailer(request.getTrailer())
                .poster(posterUrl)
                .build();
        return movieRepository.save(movie);
    }

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }


}
