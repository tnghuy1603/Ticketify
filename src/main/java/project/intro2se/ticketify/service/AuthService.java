package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.RefreshToken;
import project.intro2se.ticketify.domain.Role;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.*;
import project.intro2se.ticketify.exception.RefreshTokenException;
import project.intro2se.ticketify.repository.UserRepository;
import project.intro2se.ticketify.utils.JwtUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    public SignUpResponse signUp(SignUpRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            return SignUpResponse.builder()
                    .message("Email is already in use")
                    .build();
        }
        User user = User.builder()
                .email(request.getEmail())
                .displayName(request.getDisplayName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_CUSTOMER")
                .build();
        log.info(Role.CUSTOMER.toString());
        userRepository.save(user);
        return SignUpResponse.builder()
                    .message("Sign up successful")
                    .build();
    }

    public SignInResponse signIn(SignInRequest request) {
        log.info("In authService1");
        log.info(request.getEmail());
        log.info(request.getPassword());

        Authentication authToken = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        log.info("Testing");
        UserDetails userDetails = (UserDetails) authToken.getPrincipal();


        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = refreshTokenService.generateToken((User)userDetails).getToken();
        return SignInResponse.builder()
                .message("Sign in successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public RenewAccessTokenResponse renewAccessToken(RenewAccessTokenRequest request){
        String refreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::validateToken)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateToken(user);
                    return RenewAccessTokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                })
                .orElseThrow(()-> new RefreshTokenException("No such refresh token in database"));
    }


}
