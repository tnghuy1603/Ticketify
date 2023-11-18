package project.intro2se.ticketify.dto;

import lombok.Getter;

@Getter
public class SignInRequest {
    private String email;
    private String password;
}
