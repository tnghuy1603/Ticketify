package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.User;
import project.intro2se.ticketify.dto.AddUserRequest;
import project.intro2se.ticketify.exception.ResourceNotFoundException;
import project.intro2se.ticketify.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //Admin can only add staff or ticket manager account
    //Role correctness is ensured by client
    public User addUser(AddUserRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user =  User.builder()
                .displayName(request.getDisplayName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        return userRepository.save(user);
    }
    public User toggleUser(Long userId, boolean locked){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user with that id"));
        user.setLocked(locked);
        return userRepository.save(user);
    }
    public User enableUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user with that id"));
        user.setLocked(false);
        return userRepository.save(user);
    }

    public List<User> getAllEnabledUser(){
        return userRepository.findAllEnabledUser();
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }
    public List<User> findUserByLocked(boolean locked){
        return userRepository.findByLocked(locked);
    }
}
