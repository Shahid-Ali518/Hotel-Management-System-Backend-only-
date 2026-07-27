package com.satech.ourluxuryhotel.service.impl;

import com.satech.ourluxuryhotel.dto.request.CreateUserRequest;
import com.satech.ourluxuryhotel.dto.request.LoginRequest;
import com.satech.ourluxuryhotel.dto.response.Response;
import com.satech.ourluxuryhotel.dto.response.UserDTO;
import com.satech.ourluxuryhotel.entity.User;
import com.satech.ourluxuryhotel.entity.UserRole;
import com.satech.ourluxuryhotel.exception.AppException;
import com.satech.ourluxuryhotel.repository.UserRepository;
import com.satech.ourluxuryhotel.service.interfac.IUserService;
import com.satech.ourluxuryhotel.utils.JWTUtils;
import com.satech.ourluxuryhotel.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public Response<UserDTO> register(CreateUserRequest user) {

        Response<UserDTO> response = new Response<>();

        try{


          if(userRepository.existsByEmail(user.getEmail())){
              response.setMessage(user.getEmail() + " is Already Exists");
              throw new AppException(user.getEmail() + " is Already Exists");
          }

            User user1 = User.builder()
                    .phoneNumber(user.getPhoneNumber())
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .role(UserRole.USER)
                    .build();

            User saved = userRepository.save(user1);

            UserDTO userDTO = Utils.mapUserEntityToUserDTO(saved);
            response.setStatusCode(201); // created
            response.setData(userDTO);

        }
        catch (AppException ap){
            response.setStatusCode(400); // bad request
            response.setData(null);
            log.error(ap.getMessage());
        }
        catch (Exception e){
          response.setStatusCode(500); // internal server error
          log.error("Error occurred while registering a user");
            response.setData(null);

        }
        return response;
    }

    @Override
    public Response<UserDTO> login(LoginRequest loginRequest) {

        Response<UserDTO> response = new Response<>();
       try{

           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
           User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new AppException("user not found"));
           var token = jwtUtils.createToken(user.getEmail());
           response.setToken(token);
           response.setStatusCode(200);
           response.setRole(String.valueOf(user.getRole()));
           response.setExpirationTime("7 Days");
           response.setMessage("Successful");


       }
       catch (AppException ap){
           response.setStatusCode(404); // not found
           log.error(ap.getMessage());
       }
       catch (Exception e){
           response.setStatusCode(500); // internal server error
           System.out.println(e.getMessage());
           log.error("Error occurred while login");
       }
        return response;
    }

    @Override
    public Response<List<UserDTO>> getAllUsers() {

        Response<List<UserDTO>>  response = new Response<>();
       try{
           List<User> users = userRepository.findAll();
           List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(users);
           response.setData(userDTOList);
           response.setMessage("Successful");
           response.setStatusCode(200);
           response.setData(null);

       }
       catch (Exception e){
           response.setStatusCode(500); // internal server error
           log.error("Error occurred while getting all users");
           response.setData(null);

       }
        return response;
    }

    @Override
    public Response<UserDTO> getUserBookingHistory(String userId) {

        Response<UserDTO>  response = new Response<>();

        try{
            var user = userRepository.findById(Long.valueOf(userId)).orElseThrow( ()-> new AppException("user does not exist"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusBooking(user);

            response.setData(userDTO);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while getting user booking history");
            response.setData(null);
        }
        return response;

    }

    @Override
    public Response<UserDTO> getUserById(String userId) {
        Response<UserDTO> response = new Response<>();

        try{
            var user = userRepository.findById(Long.valueOf(userId)).orElseThrow( ()-> new AppException("user does not exist"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setData(userDTO);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }
        catch (AppException ap){
            response.setStatusCode(404); // not found
            log.error(ap.getMessage());
            response.setData(null);
        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while getting user by id");
            response.setData(null);

        }
        return response;

    }


    @Override
    public Response<UserDTO> deleteUser(String userId) {
        Response<UserDTO> response = new Response<>();
        try{
            var user = userRepository.findById(Long.valueOf(userId)).orElseThrow( ()-> new AppException("user does not exist"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("Successful");

        }
        catch (AppException ap){
            response.setStatusCode(404); // not found
            log.error(ap.getMessage());
            response.setData(null);
        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while deleting user by id");
            response.setData(null);
        }
        return response;
    }


    @Override
    public Response<UserDTO> getMyInfo(String email) {
        Response<UserDTO> response = new Response<>();
        try{
            var user = userRepository.findByEmail(email).orElseThrow( ()-> new AppException("user does not exist"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusBooking(user);

            response.setData(userDTO);
            response.setStatusCode(200);
            response.setMessage("Successful");

        }
        catch (Exception e){
            response.setStatusCode(500); // internal server error
            log.error("Error occurred while getting user info");
        }
        return response;
    }
}
