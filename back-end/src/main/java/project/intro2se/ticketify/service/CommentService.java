package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Comment;
import project.intro2se.ticketify.domain.Movie;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.AddCommentRequest;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.CommentRepository;
import project.intro2se.ticketify.repository.MovieRepository;
import project.intro2se.ticketify.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public List<Comment> getAllByMovieId(Long movieId) {
        return commentRepository.findByMovie_Id(movieId);
    }

    public Comment addComment(AddCommentRequest request, User user) {
        Comment parentComment = commentRepository.findById(request.getParentCommentId())
                .orElse(null);
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("No movie with id = " + request.getParentCommentId()));
        if(request.getRating() != null && parentComment == null){
            throw new IllegalArgumentException("Reply comment can not give a rating");
        }
        if(request.getRating().compareTo(0.0) < 0 || request.getRating().compareTo(5.0) > 0 ){
            throw new IllegalArgumentException("Rating must be greater than 0 and equal or less than 5");
        }
        Comment comment = Comment.builder()
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .parentComment(parentComment)
                .movie(movie)
                .rating(request.getRating())
                .user(user)
                .build();
        if(parentComment == null){
            return commentRepository.save(comment);
        }
        parentComment.addReply(comment);
//        commentRepository.save(parentComment);
        commentRepository.save(comment);
        return comment;
    }

}
