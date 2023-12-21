package project.intro2se.ticketify.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatMessage {
    private String type;
    private String content;
    private String sender;
}
