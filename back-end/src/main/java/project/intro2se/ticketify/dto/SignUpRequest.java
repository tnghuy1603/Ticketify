package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,24}$",
        message = "Password must be longer than 8 character and contain number, lowercase, uppercase and special character")
    private String password;
    @NotBlank(message = "Display name must contain some character")
    private String displayName;
}
