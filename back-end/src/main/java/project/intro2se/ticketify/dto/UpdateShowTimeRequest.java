package project.intro2se.ticketify.dto;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class UpdateShowTimeRequest {
    private Long showTimeId;
    private Long movieId;
    private Long roomId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
