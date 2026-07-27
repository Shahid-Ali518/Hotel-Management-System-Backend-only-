package com.satech.ourluxuryhotel.controller;

import com.satech.ourluxuryhotel.dto.request.CreateBookingRequest;
import com.satech.ourluxuryhotel.dto.response.BookingDTO;
import com.satech.ourluxuryhotel.dto.response.Response;
import com.satech.ourluxuryhotel.entity.Booking;
import com.satech.ourluxuryhotel.service.interfac.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @GetMapping("/all-bookings")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<BookingDTO>>> getAllBookings(){

        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PostMapping("/save-booking/{userId}/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response<BookingDTO>> saveBooking(@PathVariable Long userId,
                                                            @PathVariable Long roomId,
                                                            @RequestBody CreateBookingRequest request){

        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/get-booking-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response<BookingDTO>> getBookingByConfirmationCode(@PathVariable String confirmationCode) {

        return ResponseEntity.ok(bookingService.getBookingByConfirmationCode(confirmationCode));
    }

    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<Response<List<BookingDTO>>> getUserBookings(@PathVariable Long userId){

        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @DeleteMapping ("/cancel-booking/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response<?>> cancelBooking(@PathVariable Long bookingId){

        return  ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }


}
