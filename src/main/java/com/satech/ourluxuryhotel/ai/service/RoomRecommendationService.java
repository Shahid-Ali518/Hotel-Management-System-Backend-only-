package com.satech.ourluxuryhotel.ai.service;

import com.satech.ourluxuryhotel.dto.response.RoomDTO;
import com.satech.ourluxuryhotel.entity.Room;
import com.satech.ourluxuryhotel.repository.BookingRepository;
import com.satech.ourluxuryhotel.repository.RoomRepository;
import com.satech.ourluxuryhotel.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomRecommendationService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;


    public List<RoomDTO> recommendRooms(LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests,String roomType) {

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException(
                    "Check-in and check-out dates are required."
            );
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException(
                    "Check-out date must be after check-in date."
            );
        }

        if (numberOfGuests <= 0) {
            throw new IllegalArgumentException(
                    "Number of guests must be greater than zero."
            );
        }

        List<Room> rooms = roomRepository.getAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);

        List<Room> availableRooms = rooms.stream()

                // Capacity
                .filter(room ->
                        room.getCapacity() >= numberOfGuests
                )

                // Availability
                .filter(room ->
                        !bookingRepository.existsConflictingBooking(
                                room.getId(),
                                checkInDate,
                                checkOutDate
                        )
                )

                .toList();

        return Utils.mapRoomListEntityToRoomListDTO(
                availableRooms
        );
    }

}
