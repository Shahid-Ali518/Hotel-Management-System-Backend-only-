package com.satech.ourluxuryhotel.controller;

import com.satech.ourluxuryhotel.dto.request.CreateUserRequest;
import com.satech.ourluxuryhotel.dto.request.LoginRequest;
import com.satech.ourluxuryhotel.dto.response.Response;
import com.satech.ourluxuryhotel.dto.response.UserDTO;
import com.satech.ourluxuryhotel.entity.User;
import com.satech.ourluxuryhotel.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response<UserDTO>> register(@RequestBody CreateUserRequest user){
        Response<UserDTO> response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<UserDTO>> login(@RequestBody LoginRequest loginRequest){
        Response<UserDTO> response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
