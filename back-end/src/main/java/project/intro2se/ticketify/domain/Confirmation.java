package project.intro2se.ticketify.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createdAt;
    private String token;
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    public Confirmation(User user){
        this.createdAt = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.user = user;
    }
}
