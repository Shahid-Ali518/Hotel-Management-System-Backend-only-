package com.satech.ourluxuryhotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "booking")
@Data

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "check out date is required")
    private LocalDate checkOutDate;

    private Integer capacity;

    private Integer numOfGuests;

    @NotBlank(message = "code is required")
    private String bookingConfirmationCode;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @ManyToOne
    private Room room;


}
