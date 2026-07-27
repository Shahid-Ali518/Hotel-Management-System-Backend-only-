package com.satech.ourluxuryhotel.service.interfac;

import com.satech.ourluxuryhotel.dto.request.CreateUserRequest;
import com.satech.ourluxuryhotel.dto.request.LoginRequest;
import com.satech.ourluxuryhotel.dto.response.Response;
import com.satech.ourluxuryhotel.dto.response.UserDTO;
import com.satech.ourluxuryhotel.entity.User;

import java.util.List;

public interface IUserService {

    Response<UserDTO> register(CreateUserRequest user);

    Response<UserDTO> login(LoginRequest loginRequest);

    Response<List<UserDTO>> getAllUsers();

    Response<UserDTO> getUserBookingHistory(String userId);

    Response<UserDTO> getUserById(String userId);

    Response<UserDTO> deleteUser(String userId);

    Response<UserDTO> getMyInfo(String email);



}
