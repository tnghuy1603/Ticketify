package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Role;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.SignInRequest;
import project.intro2se.ticketify.dto.SignUpRequest;
import project.intro2se.ticketify.dto.SignUpResponse;
import project.intro2se.ticketify.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public SignUpResponse signUp(SignUpRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return new SignUpResponse("Email is already in use");
        }
        User user = User.builder()
                .email(request.getEmail())
                .displayName(request.getDisplayName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        return new SignUpResponse("Sign up successfully");
    }
//    public SignInResponse signIn(SignInRequest request){
//
//    }
}
