package com.satech.ourluxuryhotel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateRoomRequest {

    private String roomType;

    private Double roomPrice;

    private String description;

    private MultipartFile photo;

    private Set<String> amenities;
}
