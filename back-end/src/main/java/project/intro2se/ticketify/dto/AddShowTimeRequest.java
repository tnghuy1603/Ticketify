package project.intro2se.ticketify.dto;



import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class AddShowTimeRequest {
    private Long movieId;
    private Long roomId;
    private String startAt;
    private String endAt;
}
