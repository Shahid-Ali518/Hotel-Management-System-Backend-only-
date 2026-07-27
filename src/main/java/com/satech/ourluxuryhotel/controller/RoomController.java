package com.satech.ourluxuryhotel.controller;

import com.satech.ourluxuryhotel.dto.response.Response;
import com.satech.ourluxuryhotel.dto.response.RoomDTO;
import com.satech.ourluxuryhotel.service.interfac.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<RoomDTO>>> getAllRooms(){

        return ResponseEntity.ok(roomService.getAllRooms());
    }


    @PostMapping("/add-new-room")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<RoomDTO>> addNewRoom(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "roomType", required = false)String roomType,
            @RequestParam(value = "roomPrice", required = false)Double roomPrice,
            @RequestParam(value = "roomDescription", required = false)String roomDescription
            ){

        if(photo == null || photo.isEmpty() || roomType == null || roomType.isBlank() ||  roomPrice == null ){
            Response<RoomDTO> response = new Response<>();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(photo, type, price)");
            response.setData(null);
        }

        Response<RoomDTO> response = roomService.addNewRoom(photo,roomType, roomDescription, roomPrice);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<RoomDTO>> updateRoom(
            @PathVariable Long roomId ,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "roomType", required = false)String roomType,
            @RequestParam(value = "roomPrice", required = false)Double roomPrice,
            @RequestParam(value = "roomDescription", required = false)String roomDescription
    ){

        if(photo == null || photo.isEmpty() || roomType == null || roomType.isBlank() ||  roomPrice == null ){
            Response<RoomDTO> response = new Response<>();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(photo, type, price)");
            response.setData(null);
        }

        Response<RoomDTO> response = roomService.updateRoom(roomId, photo,roomType, roomDescription, roomPrice);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @GetMapping("/types")
    public Response<List<String>> getAllRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/get-by-id/{roomId}")
    public ResponseEntity<Response<RoomDTO>> getRoomById(@PathVariable Long roomId){
        Response<RoomDTO> response = roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response<List<RoomDTO>>> getAvailableRoomsByDateAndType(
            @RequestParam(value = "checkInDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(value = "checkOutDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
            @RequestParam(value = "roomType", required = false)String roomType
            ){

        if(checkInDate == null  || checkOutDate == null || roomType == null || roomType.isBlank()  ){
            Response<List<RoomDTO>> response = new Response<>();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(checkInDate, checkOutDate, roomType)");
        }

        Response<List<RoomDTO>> response = roomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<RoomDTO>> deleteRoom(@PathVariable Long roomId){
        Response<RoomDTO> response = roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }






}
