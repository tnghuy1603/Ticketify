package project.intro2se.ticketify.dto;

import lombok.Getter;

@Getter
public class RenewAccessTokenRequest {
    private String refreshToken;
}
