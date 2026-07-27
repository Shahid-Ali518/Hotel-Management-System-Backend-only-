package com.satech.ourluxuryhotel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "room")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;
    private Double roomPrice;
    private String description;
    private String imageUrl; // to store image url in cloudinary cloud storage
    private String imageId; // to store image id on cloudinary cloud storage, useful to delete image from cloud


    private Integer capacity;

    private Double rating;

    @ElementCollection
    private Set<String> amenities; // e.g. projector, wifi, whiteboard


    @OneToMany( mappedBy = "room", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();


}
