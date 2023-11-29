package com.example.blogapp.blogapp.users;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapp.blogapp.security.JWTService;
import com.example.blogapp.blogapp.users.dtos.CreateUserRequest;
import com.example.blogapp.blogapp.users.dtos.LoginUserRequest;
import com.example.blogapp.blogapp.users.dtos.UserResponse;
import com.example.common.ErrorResponse;

@RestController
@RequestMapping("/users")
public class UsersController {
    
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UsersController(UsersService usersService, ModelMapper modelMapper, JWTService jwtService) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> signUpUser(@RequestBody CreateUserRequest req)
    {
        UserEntity savedUser = usersService.createUser(req);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());
        var userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(jwtService.createJwt(userResponse.getId()));
        return ResponseEntity.created(savedUserUri).body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request)
    {
        UserEntity savedUser = usersService.loginUser(request.getUsername(), request.getPassword());
        var userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(jwtService.createJwt(userResponse.getId()));
        return ResponseEntity.ok(userResponse);
    }
    
    @ExceptionHandler(
        {
            UsersService.UserNotFoundException.class,
            UsersService.InvalidCredentialsException.class,
            UsersService.UserAlreadyExistsException.class
        }
    )
    ResponseEntity<ErrorResponse> handleUserExceptions(Exception ex) {
        String message;
        HttpStatus status;

        if(ex instanceof UsersService.UserNotFoundException)
        {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if(ex instanceof UsersService.InvalidCredentialsException){
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } else if(ex instanceof UsersService.UserAlreadyExistsException) {
            message = ex.getMessage();
            status = HttpStatus.FOUND;
        }
        else
        {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder()
                                .message(message)
                                .build();
                                
        return ResponseEntity.status(status).body(response);
    }
}
