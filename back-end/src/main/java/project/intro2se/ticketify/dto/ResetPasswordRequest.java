package project.intro2se.ticketify.dto;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
