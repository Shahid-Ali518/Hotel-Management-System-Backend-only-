package com.satech.ourluxuryhotel.service.interfac;

import com.satech.ourluxuryhotel.dto.request.CreateBookingRequest;
import com.satech.ourluxuryhotel.dto.response.BookingDTO;
import com.satech.ourluxuryhotel.dto.response.BookingValidationResult;
import com.satech.ourluxuryhotel.dto.response.Response;
import com.satech.ourluxuryhotel.entity.Room;
import com.satech.ourluxuryhotel.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {

    Response<BookingDTO> createBooking(CreateBookingRequest request);

    Response<BookingDTO> findBookingById(Long bookingId);

    Response<BookingDTO> getBookingByConfirmationCode(String confirmationCode);

    Response<List<BookingDTO>> getBookingsByUser(Long userId);

    Response<List<BookingDTO>>  getAllBookings();

    Response<BookingDTO> cancelBooking(Long bookingId);

    Response<BookingValidationResult> validateBooking(CreateBookingRequest request, Room room, User user);

    Response<?> hasBookingConflict(Long roomId, LocalDate checkIn, LocalDate checkOut);


}
