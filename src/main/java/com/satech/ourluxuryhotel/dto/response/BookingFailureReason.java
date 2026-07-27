package com.satech.ourluxuryhotel.dto.response;

public enum BookingFailureReason {

    NONE,

    INVALID_CHECK_IN_DATE,

    INVALID_CHECK_OUT_DATE,

    CHECK_OUT_BEFORE_CHECK_IN,

    ROOM_NOT_FOUND,

    USER_NOT_FOUND,

    ROOM_ALREADY_BOOKED,

    ROOM_CAPACITY_EXCEEDED,

    INVALID_NUMBER_OF_GUESTS
}
