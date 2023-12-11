package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.catalina.User;
@Getter
public class AddCommentRequest {
    @NotBlank(message = "Comment must contain some character")
    private String description;
    @NotNull(message = "Movie's id must not be null")
    private Long movieId;
    @NotNull(message = "Parent comment's id must not be null")
    private Long parentCommentId;
}
