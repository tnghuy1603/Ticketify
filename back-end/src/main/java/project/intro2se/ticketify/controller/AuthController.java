package project.intro2se.ticketify.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.*;
import project.intro2se.ticketify.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request){
        return ResponseEntity.ok(authService.signUp(request));
    }
    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request){
        return ResponseEntity.ok(authService.signIn(request));
    }
    @PostMapping("/renew-access-token")
    public ResponseEntity<RenewAccessTokenResponse> renewAccessToken(@RequestBody RenewAccessTokenRequest request){
        return ResponseEntity.ok(authService.renewAccessToken(request));
    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam(name = "token") @NotBlank String accessToken,
                                           @AuthenticationPrincipal User user){
        log.info(accessToken);
        return ResponseEntity.ok(authService.validateAccessToken(accessToken, user));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(authService.changePassword(changePasswordRequest, user));
    }
    @GetMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestParam String token){
        return ResponseEntity.ok(authService.verifyUser(token));
    }
    @PostMapping("/dump-user")
    public ResponseEntity<?> dumpUsers(@RequestBody List<SignUpRequest> requests){
        return ResponseEntity.ok(authService.dumpData(requests));
    }
    // for forgot password feature
    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        return ResponseEntity.ok(authService.forgotPassword(email));
    }
    @GetMapping("/reset-password")
    public ResponseEntity<?> validateRestToken(@RequestParam("token") String token){
        return ResponseEntity.ok(authService.validateRestPasswordToken(token));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> validateRestToken(@RequestBody ResetPasswordRequest request){
        return ResponseEntity.ok(authService.resetPassword(request));
    }


}
