package com.satech.ourluxuryhotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingValidationResult {

    private boolean valid;

    private BookingFailureReason reason;

    private String message;
}
