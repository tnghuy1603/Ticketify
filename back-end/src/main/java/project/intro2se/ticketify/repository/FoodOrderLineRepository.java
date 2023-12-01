package project.intro2se.ticketify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.intro2se.ticketify.domain.FoodOrderLine;
@Repository
public interface FoodOrderLineRepository extends JpaRepository<FoodOrderLine, Long> {
}
