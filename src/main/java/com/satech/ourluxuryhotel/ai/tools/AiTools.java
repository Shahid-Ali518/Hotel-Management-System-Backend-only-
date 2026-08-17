package com.satech.ourluxuryhotel.ai.tools;

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

    @Tool(name = "recommendRooms",
            description = """
            Find hotel rooms that are available for the requested dates
            and can accommodate the requested number of guests.

            Use this tool whenever the customer asks for available rooms,
            room recommendations, room suggestions, or wants to find a room
            for specific dates.

            The tool performs deterministic availability and capacity checks.
            Do not invent room information or availability.
            """
    )
    public List<RoomDTO> recommendRooms(
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int numberOfGuests,
            String roomType
    ) {

        log.info(
                "AI requested room recommendation: checkIn={}, checkOut={}, guests={}, roomType={}",
                checkInDate,
                checkOutDate,
                numberOfGuests,
                roomType
        );

        return roomRecommendationService.recommendRooms(
                checkInDate,
                checkOutDate,
                numberOfGuests,
                roomType
        );
    }

}
