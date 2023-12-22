package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AddUserRequest {
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank
    private String password;
    @NotEmpty(message = "Display name must contain character")
    private String displayName;
    @NotBlank
    private String role;
}
