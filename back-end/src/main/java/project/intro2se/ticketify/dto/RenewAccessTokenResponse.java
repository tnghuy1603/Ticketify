package project.intro2se.ticketify.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RenewAccessTokenResponse {
    private String accessToken;
    private String refreshToken;
}
