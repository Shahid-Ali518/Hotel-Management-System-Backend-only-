package com.satech.ourluxuryhotel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingRequest {

    private Long userId;

    private Long roomId;

    private Integer numOfGuests;

    private LocalDate checkIn;

    private LocalDate checkOut;
}
