package project.intro2se.ticketify.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidInput(MethodArgumentNotValidException exception){
        log.info("Invalid input exception");
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->{
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }
    @ExceptionHandler({RefreshTokenException.class, })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleRefreshToken(RefreshTokenException exception){
        log.info("Refresh token exception");
        return ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleInvalidAccess(BadCredentialsException exception){
        log.info("Authentication exception");
        return ErrorMessage.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
//    @ExceptionHandler({DisabledException.class})

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFound(ResourceNotFoundException exception){
        log.info("Resource not found exception");
        return ErrorMessage
                .builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleInternalError(Exception exception){
        log.info(exception.toString());
        log.info("Internal server errors");
        return ErrorMessage
                .builder()
                .message("Something went wrong, please try again")
                .timestamp(LocalDateTime.now())
                .build();
    }

}

