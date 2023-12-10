package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Food;
import project.intro2se.ticketify.repository.FoodRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public List<Food> getAvailableFoods() {
        return foodRepository.findAll();
    }

}
