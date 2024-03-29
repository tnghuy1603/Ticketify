package project.intro2se.ticketify.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class ExceptionResponse {
    private String message;
    private LocalDateTime timestamp;
}
