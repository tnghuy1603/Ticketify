package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.*;
import project.intro2se.ticketify.dto.*;
import project.intro2se.ticketify.exception.RefreshTokenException;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.ConfirmationRepository;
import project.intro2se.ticketify.repository.ResetPasswordTokenRepository;
import project.intro2se.ticketify.repository.UserRepository;
import project.intro2se.ticketify.utils.JwtUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationRepository confirmationRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final MailService mailService;
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
                .isLocked(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_CUSTOMER")
                .build();

        userRepository.save(user);
        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
        String confirmationLink = "http://localhost:5173/verify-account?token=" + confirmation.getToken();
        mailService.sendSimpleEmail(request.getEmail(), "Confirm new account", MailService.CONFIRM_EMAIL_MSG + confirmationLink);
        return SignUpResponse.builder()
                    .message("Check your email to verify account")
                    .build();

    }

    public SignInResponse signIn(SignInRequest request) {

        Authentication authToken = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authToken.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authToken);

        String accessToken = jwtUtils.generateToken(userDetails);
        String refreshToken = refreshTokenService.generateToken((User)userDetails).getToken();
        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Logged in")
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
    public boolean validateAccessToken(String accessToken, User user){
        return jwtUtils.validateToken(accessToken, user);
    }


    public CustomResponse validateRestPasswordToken(String token){
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("No reset password token =" + token));
        if(resetPasswordToken.getExpireDate().isBefore(LocalDateTime.now())){
            throw new ResourceNotFoundException("Reset token is expired");
        }
        return CustomResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Token is valid")
                .build();
    }
    public CustomResponse forgotPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Not found any user with email =" + email));
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user);
        resetPasswordTokenRepository.save(resetPasswordToken);
        String resetLink = "http://localhost:5173/reset-password?token="+ resetPasswordToken.getToken();
        mailService.sendSimpleEmail(email, "Reset your ticketify account password", MailService.contentOfResetPassword(resetLink));
        return CustomResponse.builder()
                .data(null)
                .message("Check your email to reset password")
                .build();
    }
    public CustomResponse resetPassword(ResetPasswordRequest request){
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("No reset password token =" + request.getToken() ));
        User user = resetPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        resetPasswordTokenRepository.delete(resetPasswordToken);
        userRepository.save(user);
        return new CustomResponse("Your password has been change", null, LocalDateTime.now());
    }

    public CustomResponse changePassword(ChangePasswordRequest changePasswordRequest, User user){
        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Incorrect old password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return new CustomResponse("Password changed", user, LocalDateTime.now());
    }


    public String dumpData(List<SignUpRequest> requests){
        for(SignUpRequest request: requests){
            User user = User.builder()
                    .email(request.getEmail())
                    .displayName(request.getDisplayName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role("ROLE_CUSTOMER")
                    .isLocked(false)
                    .build();

            log.info(Role.CUSTOMER.toString());
            userRepository.save(user);
        }
        return "Done";
    }
    public CustomResponse verifyUser(String token){
        Confirmation confirmation = confirmationRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Not found any token = " + token));
        User user = confirmation.getUser();
        user.setLocked(false);
        userRepository.save(user);
        confirmationRepository.delete(confirmation);
        return new CustomResponse("Account verified", user, LocalDateTime.now());
    }




}
