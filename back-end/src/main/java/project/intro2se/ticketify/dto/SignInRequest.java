package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignInRequest {
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,40}$",
            message = "Password must be longer than 8 character and contain number, lowercase, uppercase and special character")
    private String password;
}
