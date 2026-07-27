package com.satech.ourluxuryhotel.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private Long id;

    private String roomType;
    private Double roomPrice;
    private String description;
    private String imageUrl;

    private Set<String> amenities;


    private List<BookingDTO> bookings;
}
