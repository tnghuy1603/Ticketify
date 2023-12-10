package project.intro2se.ticketify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.intro2se.ticketify.service.FoodService;
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    public ResponseEntity<?> getAllFood(){
        return ResponseEntity.ok(foodService.getAvailableFoods());
    }


}
