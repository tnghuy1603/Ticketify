package project.intro2se.ticketify.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.AddCommentRequest;
import project.intro2se.ticketify.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentController {
    private final CommentService commentService;
    @GetMapping()
    public ResponseEntity<?> getCommentsOfMovie(@RequestParam(name = "movie") @Min(value = 0L, message = "Movie's id must be positive") Long movieId){
        return ResponseEntity.ok(commentService.getAllByMovieId(movieId));
    }
    @PostMapping
    public ResponseEntity<?> getCommentsOfMovie(@Valid @RequestBody AddCommentRequest request, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(commentService.addComment(request, user));
    }
}
