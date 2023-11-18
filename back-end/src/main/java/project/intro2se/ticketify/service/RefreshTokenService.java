package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.RefreshToken;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.exception.RefreshTokenException;
import project.intro2se.ticketify.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refresh-token-duration-in-ms}")
    private Long durationInMs;
    Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
    public RefreshToken generateToken(User user){
        RefreshToken refreshToken =  RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expireAt(Instant.now().plusMillis(durationInMs))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public RefreshToken validateToken(RefreshToken refreshToken){
        if(refreshToken.getExpireAt().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Token is expired. Please sign in again");
        }
        return refreshToken;
    }
    public void deleteByUser(User user){
        refreshTokenRepository.deleteByUser(user);
    }

}
