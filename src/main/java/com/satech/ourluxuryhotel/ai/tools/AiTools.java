package com.satech.ourluxuryhotel.ai.tools;

import com.satech.ourluxuryhotel.ai.service.DateResolutionService;
import com.satech.ourluxuryhotel.ai.service.RoomRecommendationService;
import com.satech.ourluxuryhotel.dto.response.RoomDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiTools {

    private final RoomRecommendationService roomRecommendationService;
    private final DateResolutionService dateResolutionService;

    private List<RoomDTO> recommendedRooms = List.of();


    @Tool(
            name = "recommendRooms",
            description = """
            Search for hotel rooms based on the customer's requirements.

            Use this tool whenever the customer wants to find,
            search for, or recommend available rooms.

            checkInDate:
            The customer's requested check-in date.
            Examples:
            - today
            - tomorrow
            - day after tomorrow
            - 2026-08-20

            checkOutDate:
            The customer's requested check-out date.
            Examples:
            - tomorrow
            - 2026-08-22
            - 4 days

            numberOfGuests:
            Total number of guests.

            roomType:
            Optional room type. Use null when the customer does not
            specify a room type.

            Do NOT perform date arithmetic yourself.
            Pass the customer's date expression to the tool.
            The application resolves dates deterministically.
            """
    )
    public List<RoomDTO> recommendRooms(
            String checkInDate,
            String checkOutDate,
            Integer numberOfGuests,
            String roomType
    ) {


        LocalDate resolvedCheckIn =
                dateResolutionService.resolve(checkInDate);

        LocalDate resolvedCheckOut =
                dateResolutionService.resolveRelativeTo(
                        checkOutDate,
                        resolvedCheckIn
                );

        log.info(
                "Resolved dates -> checkIn={}, checkOut={}",
                resolvedCheckIn,
                resolvedCheckOut
        );

        recommendedRooms =  roomRecommendationService.recommendRooms(
                resolvedCheckIn,
                resolvedCheckOut,
                numberOfGuests,
                roomType
        );

        return recommendedRooms;
    }

    public List<RoomDTO> getRecommendedRooms() {
        return recommendedRooms;
    }

}
