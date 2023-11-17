package project.intro2se.ticketify.dto;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    String oldPassword;
    String newPassword;
}
