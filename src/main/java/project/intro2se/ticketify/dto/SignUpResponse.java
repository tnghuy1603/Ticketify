package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpResponse {

    private String message;
}
