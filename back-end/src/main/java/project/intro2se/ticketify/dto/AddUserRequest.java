package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AddUserRequest {
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty()
    private String password;
    @NotEmpty(message = "Display name must contain character")
    private String displayName;

    private String role;
}
