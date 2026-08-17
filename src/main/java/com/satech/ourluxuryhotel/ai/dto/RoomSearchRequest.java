package com.satech.ourluxuryhotel.ai.dto;

public record RoomSearchRequest(
        String checkInDate,

        String checkOutDate,

        Integer numberOfGuests,

        String roomType

) {
}
