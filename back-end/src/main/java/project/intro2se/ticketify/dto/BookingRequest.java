package project.intro2se.ticketify.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BookingRequest {
    public List<Long> ticketIds;
    public List<FoodOrderLineDto> orderLineDtoList;

}

