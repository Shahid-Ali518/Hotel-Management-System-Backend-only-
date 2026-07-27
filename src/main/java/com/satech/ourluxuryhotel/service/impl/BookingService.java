package com.satech.ourluxuryhotel.service.impl;

import com.satech.ourluxuryhotel.dto.request.CreateBookingRequest;
import com.satech.ourluxuryhotel.dto.response.*;
import com.satech.ourluxuryhotel.entity.Booking;
import com.satech.ourluxuryhotel.entity.BookingStatus;
import com.satech.ourluxuryhotel.entity.Room;
import com.satech.ourluxuryhotel.entity.User;
import com.satech.ourluxuryhotel.exception.AppException;
import com.satech.ourluxuryhotel.repository.BookingRepository;
import com.satech.ourluxuryhotel.repository.RoomRepository;
import com.satech.ourluxuryhotel.repository.UserRepository;
import com.satech.ourluxuryhotel.service.interfac.IBookingService;
import com.satech.ourluxuryhotel.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    // to get all bookings
    @Override
    public Response<List<BookingDTO>>  getAllBookings() {

        Response<List<BookingDTO>> response = Response.<List<BookingDTO>>builder()
                .statusCode(200)
                .message("Success")
                .data(null)
                .build();

        try{
            List<Booking> bookings = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookings);
            response.setData(bookingDTOList);
            response.setMessage("successful");
            response.setStatusCode(200);
        }
        catch (AppException ap){
            response.setStatusCode(404); // not found request
            log.error(ap.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while getting all bookings{} ", e.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public Response<BookingDTO> createBooking(CreateBookingRequest request) {



        try {

            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new AppException("Room not found"));

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new AppException("User not found"));

            // Validate booking request
            Response<BookingValidationResult> validationResponse = validateBooking(request, room, user);

            if (!validationResponse.getData().isValid()) {
                return Response.<BookingDTO>builder()
                        .statusCode(validationResponse.getStatusCode())
                        .message(validationResponse.getData().getMessage())
                        .data(null)
                        .build();
            }

            String confirmationCode = Utils.generateRandomConfirmationCode(10);

            Booking booking = Booking.builder()
                    .bookingConfirmationCode(confirmationCode)
                    .checkInDate(request.getCheckIn())
                    .checkOutDate(request.getCheckOut())
                    .numOfGuests(request.getNumOfGuests())
                    .status(BookingStatus.CONFIRMED)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .room(room)
                    .user(user)
                    .build();

            Booking savedBooking = bookingRepository.save(booking);

            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(savedBooking);

            return Response.<BookingDTO>builder()
                    .statusCode(201)
                    .message("Booking created successfully.")
                    .confirmationCode(confirmationCode)
                    .data(bookingDTO)
                    .build();

        } catch (AppException ex) {

            log.error(ex.getMessage());

            return Response.<BookingDTO>builder()
                    .statusCode(404)
                    .message(ex.getMessage())
                    .data(null)
                    .build();

        } catch (Exception ex) {

            log.error("Error creating booking", ex);

            return Response.<BookingDTO>builder()
                    .statusCode(500)
                    .message("Internal server error while creating booking.")
                    .data(null)
                    .build();
        }
    }



    @Override
    public Response<BookingDTO> getBookingByConfirmationCode(String bookingConfirmationCode) {
        Response<BookingDTO> response = Response.<BookingDTO>builder()
                .statusCode(200)
                .message("Success")
                .data(null)
                .build();
        try{
            Booking booking = bookingRepository.findBookingByBookingConfirmationCode(bookingConfirmationCode).orElseThrow(()-> new AppException("Booking not found"));

            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRoom(booking, true);
            response.setData(bookingDTO);
            response.setMessage("successful");
            response.setStatusCode(200);
        }
        catch (AppException ap){
            response.setStatusCode(404); // not found request
            log.error(ap.getMessage());
            response.setData(null);
        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while getting booking via code");
            response.setData(null);
        }
        return response;
    }

    @Override
    public Response<List<BookingDTO>>  getBookingsByUser(Long userId) {
        Response<List<BookingDTO>> response = Response.<List<BookingDTO>>builder()
                .statusCode(200)
                .message("Success")
                .data(null)
                .build();
        try{

            User user = userRepository.findById(userId).orElseThrow(()-> new AppException("User does not found"));

            List<Booking> bookingList = bookingRepository.findBookingByUserId(userId);
            if(bookingList != null && !bookingList.isEmpty()){
                List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
                response.setData(bookingDTOList);
                response.setMessage("successful");
                response.setStatusCode(200);
            }
        }
        catch (AppException ap){
            response.setStatusCode(404); // not found request
            response.setData(null);
            log.error(ap.getMessage());

        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            response.setData(null);
            log.error("Error occurred while loading bookings by userId");
        }
        return response;
    }

    @Override
    public Response<BookingDTO> findBookingById(Long bookingId) {
        Response<BookingDTO> response = Response.<BookingDTO>builder()
                .statusCode(200)
                .message("Success")
                .data(null)
                .build();
        try{
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new AppException("Booking not found"));

            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRoom(booking, true);
            response.setData(bookingDTO);
            response.setMessage("successful");
            response.setStatusCode(200);
        }
        catch (AppException ap){
            response.setStatusCode(404); // not found request
            log.error(ap.getMessage());
            response.setData(null);
        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while getting booking with Id:{} ", bookingId );
            response.setData(null);
        }
        return response;
    }

    @Override
    @Transactional
    public Response<BookingDTO> cancelBooking(Long bookingId) {
        Response<BookingDTO> response = Response.<BookingDTO>builder()
                .statusCode(200)
                .message("Success")
                .data(null)
                .build();
        try{
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new AppException("Booking does not exist"));

            booking.setStatus(BookingStatus.CANCELLED);
            booking.setUpdatedAt(LocalDateTime.now());

            bookingRepository.save(booking);

            response.setMessage("Booking of Id " + bookingId + " is marked as CANCELLED!");
            response.setStatusCode(200);
            response.setData(Utils.mapBookingEntityToBookingDTO(booking));
        }
        catch (AppException ap){
            response.setStatusCode(404);
            response.setData(null);// not found request
            log.error(ap.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while canceling booking ");
            response.setData(null);
        }
        return response;
    }

    @Override
    public Response<BookingValidationResult> validateBooking(CreateBookingRequest request, Room room, User user) {

            Response<BookingValidationResult> response = new Response<>();

            try {

                // check dates
                if (request.getCheckIn() == null || request.getCheckOut() == null) {

                    response.setStatusCode(400);
                    response.setMessage("Check-in and Check-out dates are required.");

                    response.setData(
                            BookingValidationResult.builder()
                                    .valid(false)
                                    .reason(BookingFailureReason.INVALID_CHECK_IN_DATE)
                                    .message("Booking dates are required.")
                                    .build());

                    return response;
                }

                if (!request.getCheckOut().isAfter(request.getCheckIn())) {

                    response.setStatusCode(400);
                    response.setMessage("Check-out date must be after Check-in date.");

                    response.setData(
                            BookingValidationResult.builder()
                                    .valid(false)
                                    .reason(BookingFailureReason.CHECK_OUT_BEFORE_CHECK_IN)
                                    .message("Invalid booking duration.")
                                    .build());

                    return response;
                }

                // room exists
                if (room == null) {

                    response.setStatusCode(404);

                    response.setData(
                            BookingValidationResult.builder()
                                    .valid(false)
                                    .reason(BookingFailureReason.ROOM_NOT_FOUND)
                                    .message("Room not found.")
                                    .build());

                    return response;
                }

                // user exists
                if (user == null) {

                    response.setStatusCode(404);

                    response.setData(
                            BookingValidationResult.builder()
                                    .valid(false)
                                    .reason(BookingFailureReason.USER_NOT_FOUND)
                                    .message("User not found.")
                                    .build());

                    return response;
                }

                // guest validation
                if (request.getNumOfGuests() <= 0) {

                    response.setStatusCode(400);

                    response.setData(
                            BookingValidationResult.builder()
                                    .valid(false)
                                    .reason(BookingFailureReason.INVALID_NUMBER_OF_GUESTS)
                                    .message("At least one guest is required.")
                                    .build());

                    return response;
                }

                // capacity validation
                if (room.getCapacity() < request.getNumOfGuests()) {

                    response.setStatusCode(400);

                    response.setData(
                            BookingValidationResult.builder()
                                    .valid(false)
                                    .reason(BookingFailureReason.ROOM_CAPACITY_EXCEEDED)
                                    .message("Room capacity exceeded.")
                                    .build());

                    return response;
                }

                // booking conflict
                boolean conflict = bookingRepository.existsConflictingBooking(request.getRoomId(), request.getCheckIn(), request.getCheckOut());

                if (conflict) {

                    response.setStatusCode(409);

                    response.setData(
                            BookingValidationResult.builder()
                                    .valid(false)
                                    .reason(BookingFailureReason.ROOM_ALREADY_BOOKED)
                                    .message("Room is already booked for the selected dates.")
                                    .build());

                    return response;
                }

                response.setStatusCode(200);
                response.setMessage("Booking validation successful.");

                response.setData(
                        BookingValidationResult.builder()
                                .valid(true)
                                .reason(BookingFailureReason.NONE)
                                .message("Booking is valid.")
                                .build());

            }
            catch (Exception ex) {

                response.setStatusCode(500);
                response.setMessage(ex.getMessage());
            }

            return response;
        }

    @Override
    public Response<Boolean> hasBookingConflict(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut) {

        Response<Boolean> response = new Response<>();

        try {

            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new AppException("Room not found"));

            boolean conflict = bookingRepository.existsConflictingBooking(
                    room.getId(),
                    checkIn,
                    checkOut);

            response.setStatusCode(200);
            response.setMessage(conflict
                    ? "Room already booked."
                    : "Room available.");

            response.setData(conflict);

        }
        catch (AppException ex) {

            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex) {

            response.setStatusCode(500);
            response.setMessage("Internal Server Error");
        }

        return response;
    }

}
