package com.example.blogapp.blogapp.users;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blogapp.blogapp.users.dtos.CreateUserRequest;

@Service
public class UsersService {
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest req)
    {
        var isUserExists = usersRepository.findByUsername(req.getUsername());
        if(!isUserExists.equals(Optional.empty()))
        {
            throw new UserAlreadyExistsException(req.getUsername());
        }
        UserEntity user = modelMapper.map(req, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public UserEntity getUser(String username)
    {
        var user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return user;
    }

    public UserEntity getUser(Long id)
    {
        var user = usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    public UserEntity loginUser(String username, String password)
    {
        var user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        var isPasswordEqual = passwordEncoder.matches(password, user.getPassword());
        if(!isPasswordEqual)
            throw new InvalidCredentialsException();
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username)
        {
            super("User " + username + " Not found");
        }

        public UserNotFoundException(Long userId)
        {
            super("User " + userId + " Not found");
        }
    }

    public static class InvalidCredentialsException extends IllegalArgumentException {
        public InvalidCredentialsException()
        {
            super("Invalid username or password combination");
        }
    }

    public static class UserAlreadyExistsException extends IllegalArgumentException {
        public UserAlreadyExistsException(String username) {
            super("User with username: " + username + " Already Exists");
        }
    }
}
