package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentDto {
    private String status;
    private String message;
    private String callBackUrl;
}
