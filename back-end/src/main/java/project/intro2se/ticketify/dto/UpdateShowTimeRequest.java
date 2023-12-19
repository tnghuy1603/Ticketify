package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class UpdateShowTimeRequest {
    @NotNull(message = "Showtime's id must not be null")
    private Long showTimeId;
    @NotNull(message = "Movie's id must not be null")
    private Long movieId;
    @NotNull(message = "Room's id must not be null")
    private Long roomId;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
