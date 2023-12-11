package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ForgotPasswordRequest {
    @Email
    private String email;
    @NotEmpty

    private String oldPassword;
    private String newPassword;
}
