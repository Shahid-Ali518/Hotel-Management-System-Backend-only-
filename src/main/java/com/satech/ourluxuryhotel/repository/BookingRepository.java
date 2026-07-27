package com.satech.ourluxuryhotel.repository;

import com.satech.ourluxuryhotel.entity.Booking;
import com.satech.ourluxuryhotel.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findBookingByBookingConfirmationCode(String bookingConfirmationCode);

    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.user.id = :userId
            ORDER BY b.checkInDate DESC
            """)
    List<Booking> findBookingByUserId(@Param("userId") Long userId);


    List<Booking> findByRoomId(Long roomId);


    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.room.id = :roomId
            ORDER BY b.checkInDate
            """)
    List<Booking> findRoomBookingHistory(@Param("roomId") Long roomId);


    /**
     * Check whether a room is already booked
     * for the requested period.
     */
    @Query("""
            SELECT COUNT(b) > 0
            FROM Booking b
            WHERE b.room.id = :roomId
              AND b.status = com.satech.ourluxuryhotel.entity.BookingStatus.CONFIRMED
              AND b.checkInDate < :checkOutDate
              AND b.checkOutDate > :checkInDate
            """)
    boolean existsConflictingBooking(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );


    // AI Tool calling
    // Current staying guests
    List<Booking> findByStatus(BookingStatus status);

    // Room booking history
    List<Booking> findByRoomIdOrderByCheckInDateDesc(Long roomId);

    // User booking history
    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Bookings between dates
    List<Booking> findByCheckInDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    // Check-outs for a specific date
    List<Booking> findByCheckOutDate(LocalDate checkOutDate);

    // Active bookings for a room
    @Query("""
       SELECT b
       FROM Booking b
       WHERE b.room.id = :roomId
         AND b.status = :status
       ORDER BY b.checkInDate
       """)
    List<Booking> findActiveBookingsForRoom(
            @Param("roomId") Long roomId,
            @Param("status") BookingStatus status
    );

}
