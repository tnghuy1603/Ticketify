package project.intro2se.ticketify.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class BookingRequest {
    @NotNull
    public List<Long> ticketIds;
    @NotNull
    public List<FoodOrderLineDto> orderLineDtoList;

}

