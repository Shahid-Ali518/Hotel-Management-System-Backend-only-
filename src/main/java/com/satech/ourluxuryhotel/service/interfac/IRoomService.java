package com.satech.ourluxuryhotel.service.interfac;

import com.satech.ourluxuryhotel.dto.response.Response;
import com.satech.ourluxuryhotel.dto.response.RoomDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {


    Response<RoomDTO> addNewRoom(MultipartFile photo, String roomType, String description, Double roomPrice, Integer capacity, Double rating);

    Response<List<RoomDTO>> getAllRooms();

    Response<List<String>> getAllRoomTypes();

    Response<RoomDTO> deleteRoom(Long roomId);

    Response<RoomDTO> updateRoom(Long roomId, MultipartFile photo, String roomType, String description, Double roomPrice );

    Response<RoomDTO> getRoomById(Long roomId);

    Response<List<RoomDTO>> getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

}
