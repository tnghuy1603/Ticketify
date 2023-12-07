package project.intro2se.ticketify.dto;

import lombok.Getter;

@Getter
public class AddUserRequest {
    private String email;
    private String password;
    private String displayName;
    private String role;
}
