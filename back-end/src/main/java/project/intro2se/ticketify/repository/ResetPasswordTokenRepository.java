package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.ResetPasswordToken;

import java.util.Optional;

@Service
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByToken(String token);
}
