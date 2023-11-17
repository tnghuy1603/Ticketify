package project.intro2se.ticketify.dto;

import lombok.Getter;

@Getter
public class ForgotPasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
