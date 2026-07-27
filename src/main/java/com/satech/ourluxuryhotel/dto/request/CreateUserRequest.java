package com.satech.ourluxuryhotel.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserRequest {

    private String name;

    private String email;

    private String password;

    private String phoneNumber;
}
