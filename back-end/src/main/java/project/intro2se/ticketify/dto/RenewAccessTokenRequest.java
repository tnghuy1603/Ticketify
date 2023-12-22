package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RenewAccessTokenRequest {
    @NotBlank
    private String refreshToken;
}
