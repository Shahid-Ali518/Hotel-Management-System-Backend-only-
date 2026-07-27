package com.satech.ourluxuryhotel.utils;

import com.satech.ourluxuryhotel.dto.response.BookingDTO;
import com.satech.ourluxuryhotel.dto.response.RoomDTO;
import com.satech.ourluxuryhotel.dto.response.UserDTO;
import com.satech.ourluxuryhotel.entity.Booking;
import com.satech.ourluxuryhotel.entity.Room;
import com.satech.ourluxuryhotel.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom secureRandom = new SecureRandom();

    // method to generate confirmation code
    public static String generateRandomConfirmationCode(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);

        }
        return stringBuilder.toString();
    }

// user to userDTO
    public static UserDTO mapUserEntityToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setRole(String.valueOf(user.getRole()));
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }

// user to userDTO with bookings
    public static UserDTO mapUserEntityToUserDTOPlusBooking(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setRole(String.valueOf(user.getRole()));
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        if(!user.getBookings().isEmpty()){
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedRoom(booking, true)).toList());
        }
        return userDTO;
    }

// room to roomDTO
    public static RoomDTO mapRoomEntityToRoomDTO(Room room){

        return RoomDTO.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomPrice(room.getRoomPrice())
                .description(room.getDescription())
                .imageUrl(room.getImageUrl())
                .amenities(room.getAmenities())

                .build();
    }

// room to roomDTO with booking
    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room){
        return RoomDTO.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .roomPrice(room.getRoomPrice())
                .description(room.getDescription())
                .imageUrl(room.getImageUrl())
                .amenities(room.getAmenities())
                .bookings(mapBookingListEntityToBookingListDTO(room.getBookings()))
                .build();
    }

// booking to bookingDTO
    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking){

        return BookingDTO.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .bookingConfirmationCode(booking.getBookingConfirmationCode())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }

// booking to bookingDTO with room and user
    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedRoom(Booking booking, boolean mapUser){
        return BookingDTO.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .bookingConfirmationCode(booking.getBookingConfirmationCode())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .updatedAt(booking.getUpdatedAt())
                .roomDTO(booking.getRoom() != null ? mapRoomEntityToRoomDTO(booking.getRoom()) : null)
                .build();
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList){
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList){
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }


}
