package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.intro2se.ticketify.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMovie_Id(Long movieId);
}
