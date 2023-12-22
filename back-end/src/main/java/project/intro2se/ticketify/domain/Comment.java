package project.intro2se.ticketify.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double rating;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> relies;
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;
    public Comment addReply(Comment comment){
        relies.add(comment);
        comment.setParentComment(this);
        return comment;
    }



}
