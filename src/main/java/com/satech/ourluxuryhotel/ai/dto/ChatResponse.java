package com.satech.ourluxuryhotel.ai.dto;

import com.satech.ourluxuryhotel.dto.response.RoomDTO;

import java.util.List;

public record ChatResponse(

        String reply,

        List<RoomDTO> recommendedRooms

) {}