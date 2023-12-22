package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CreateTransactionSession {
    private String status;
    private String orderId;
    private String redirectUrl;
}
