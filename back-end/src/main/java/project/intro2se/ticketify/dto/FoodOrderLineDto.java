package project.intro2se.ticketify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FoodOrderLineDto {
    private Long foodId;
    private int quantity;
}
