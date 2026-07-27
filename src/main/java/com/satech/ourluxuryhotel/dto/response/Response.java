package com.satech.ourluxuryhotel.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String confirmationCode;

    private T data;

}
