package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.dto.SignUpRequest;
import project.intro2se.ticketify.dto.SignUpResponse;
import project.intro2se.ticketify.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
