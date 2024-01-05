package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.intro2se.ticketify.domain.User;

import project.intro2se.ticketify.dto.AddUserRequest;
import project.intro2se.ticketify.dto.ToggleUserRequest;
import project.intro2se.ticketify.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping
    public ResponseEntity<User> toggleUser(@RequestParam("user") Long userId, @RequestBody ToggleUserRequest request){
        return ResponseEntity.ok(userService.toggleUser(userId, request.isLocked()));
    }
    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(name = "locked", required = false) Boolean locked) {
        if (locked == null) {
            return ResponseEntity.ok(userService.findAll());
        }
        return ResponseEntity.ok(userService.findUserByLocked(locked));
    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody AddUserRequest request){
        return ResponseEntity.status(201).body(userService.addUser(request));
    }


}
