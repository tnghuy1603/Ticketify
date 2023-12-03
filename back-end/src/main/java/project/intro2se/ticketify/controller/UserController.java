package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.AddUserRequest;
import project.intro2se.ticketify.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping
    public ResponseEntity<User> disableUser(@RequestParam("user") Long userId){
        return ResponseEntity.ok(userService.disableUser(userId));
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllEnabledUser(){
        return ResponseEntity.ok(userService.getAllEnabledUser());
    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody AddUserRequest request){
        return ResponseEntity.status(201).body(userService.addUser(request));
    }
}
