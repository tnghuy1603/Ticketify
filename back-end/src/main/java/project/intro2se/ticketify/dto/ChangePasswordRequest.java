package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String oldPassword;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,24}$",
            message = "\"New password must be longer than 8 character and contain number, lowercase, uppercase and special character\"")
    private String newPassword;
}
