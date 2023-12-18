package project.intro2se.ticketify.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class AddShowTimeRequest {
    @NotNull
    private Long movieId;
    @NotNull
    private Long roomId;
    private String startAt;
    private String endAt;
}
