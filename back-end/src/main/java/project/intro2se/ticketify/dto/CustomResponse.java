package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class CustomResponse {
    private String message;
    private Object data;
    private LocalDateTime timestamp;
}
