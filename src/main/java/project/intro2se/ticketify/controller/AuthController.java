package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.intro2se.ticketify.dto.SignUpRequest;
import project.intro2se.ticketify.dto.SignUpResponse;
import project.intro2se.ticketify.service.AuthService;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest request){
        return ResponseEntity.ok(authService.signUp(request));
    }

}
