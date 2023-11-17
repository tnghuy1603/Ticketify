package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.*;
import project.intro2se.ticketify.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(authService.signUp(request));
    }
    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request){
        return ResponseEntity.ok(authService.signIn(request));
    }
    @PostMapping("/renew-access-token")
    public ResponseEntity<RenewAccessTokenResponse> renewAccessToken(@RequestBody RenewAccessTokenRequest request){
        return ResponseEntity.ok(authService.renewAccessToken(request));
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam(name = "token") String accessToken, @AuthenticationPrincipal User user){
        log.info(accessToken);
        return ResponseEntity.ok(authService.validateToken(accessToken, user));
    }

}
