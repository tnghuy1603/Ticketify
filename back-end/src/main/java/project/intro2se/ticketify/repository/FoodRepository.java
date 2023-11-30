package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.intro2se.ticketify.domain.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
