package project.intro2se.ticketify.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.intro2se.ticketify.dto.ResetPasswordRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime expireDate;
    public ResetPasswordToken(User user){
        this.user = user;
        this.expireDate = LocalDateTime.now().plusMinutes(30);
        this.token = UUID.randomUUID().toString();
    }

}
