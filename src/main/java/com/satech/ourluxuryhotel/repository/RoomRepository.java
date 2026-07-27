package com.satech.ourluxuryhotel.repository;

import com.satech.ourluxuryhotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find all available room types
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    // All rooms
    @Query("SELECT r FROM Room r")
    List<Room> getAvailableRooms();


    /**
     * Find rooms available for the given date range.
     *
     * A room is available if there is NO confirmed booking
     * whose date overlaps the requested dates.
     */
    @Query("""
            SELECT r
            FROM Room r
            WHERE (:roomType IS NULL OR r.roomType = :roomType)
            AND NOT EXISTS (
                SELECT b
                FROM Booking b
                WHERE b.room = r
                  AND b.status = com.satech.ourluxuryhotel.entity.BookingStatus.CONFIRMED
                  AND b.checkInDate < :checkOutDate
                  AND b.checkOutDate > :checkInDate
            )
            """)
    List<Room> getAvailableRoomsByDatesAndTypes(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomType") String roomType
    );



    List<Room> findByRoomType(String roomType);


    // For AI Tool calling
    List<Room> findByCapacityGreaterThanEqual(Integer capacity);

    List<Room> findByRoomPriceLessThanEqual(Double price);

    List<Room> findByRatingGreaterThanEqual(Double rating);

}
