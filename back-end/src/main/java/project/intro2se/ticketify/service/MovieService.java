package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.dto.AddMovieRequest;
import project.intro2se.ticketify.dto.CustomResponse;
import project.intro2se.ticketify.dto.UpdateMovieRequest;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.MovieRepository;
import project.intro2se.ticketify.repository.ShowTimeRepository;

import java.time.LocalDateTime;
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
    private final ShowTimeRepository showTimeRepository;
    // moviegoer
    public Movie findById(Long movieId){
        return movieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("No movie with that id"));
    }
    public List<Movie> findByStatus(String status){
        return movieRepository.findByStatus(status);
    }

    //ticket manager
    public Movie update(UpdateMovieRequest request, MultipartFile file) throws ExecutionException, InterruptedException {

        Movie movieToUpdate = movieRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No movie with id = "  ));
        String posterUrl = movieToUpdate.getPoster();
        if(file != null){
            Map data = cloudinaryService.upload(file).get();
            posterUrl = (String) data.get("url");
        }
        movieToUpdate.setPoster(posterUrl);
        movieToUpdate.setCast(request.getCast());
        movieToUpdate.setDuration(request.getDuration());
        movieToUpdate.setDirector(request.getDirector());
        movieToUpdate.setGenre(request.getGenre());
        movieToUpdate.setOpeningDay(request.getOpeningDay());
        movieToUpdate.setRated(request.getRated());
        movieToUpdate.setStatus(request.getStatus());
        movieToUpdate.setTitle(request.getTitle());
        movieToUpdate.setLanguage(request.getLanguage());
        movieToUpdate.setStory(request.getStory());
        movieToUpdate.setTrailer(request.getTrailer());

        return movieRepository.save(movieToUpdate);
    }
    public Movie add(AddMovieRequest request, MultipartFile file) throws ExecutionException, InterruptedException {
        Map data = cloudinaryService.upload(file).get();
        String posterUrl = (String) data.get("url");
        log.info("Url: " + posterUrl);

        Movie movie = Movie.builder()
                .cast(request.getCast())
                .status("Upcoming")
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


    public CustomResponse deleteById(Long movieId) {
        if(showTimeRepository.existsShowTimeByMovie_Id(movieId)){
            return new CustomResponse("There are some showtimes for this movie. Can not delete it", null, LocalDateTime.now());
        }
        movieRepository.deleteById(movieId);
        return new CustomResponse("Delete success fully", null, LocalDateTime.now());
    }
}
