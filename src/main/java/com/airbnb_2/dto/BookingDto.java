package com.airbnb_2.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingDto {
    private Long bookingId;
    private String guestName;
    private int price;
    private int totalPrice;
}
