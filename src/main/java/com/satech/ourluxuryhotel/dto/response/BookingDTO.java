package com.satech.ourluxuryhotel.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.satech.ourluxuryhotel.entity.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private int numOfGuests;

    private String bookingConfirmationCode;

    private UserDTO userDTO;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private RoomDTO roomDTO;
}
