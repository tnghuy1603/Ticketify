package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignInRequest {
    @Email(message = "Invalid email format")
    private String email;

    private String password;
}
