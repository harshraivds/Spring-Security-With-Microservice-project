package com.authentication.service;

import com.authentication.dto.APIResponse;
import com.authentication.dto.UserDto;
import com.authentication.entity.User;
import com.authentication.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    public APIResponse<?> register(UserDto userDto) {

        APIResponse<Object> response = new APIResponse<>();

        // Check if username exists
        if (userRepository.existsByUsername(userDto.getUsername())) {
            response.setMessage("Username already exists");
            response.setStatus(500);
            return response;
        }

        // Check if email exists
        if (userRepository.existsByEmail(userDto.getEmail())) {
            response.setMessage("Email already exists");
            response.setStatus(500);
            return response;
        }

        // Create entity and encrypt password
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 🔐 Encrypts with BCrypt

        userRepository.save(user);

        response.setMessage("User registered successfully");
        response.setStatus(201);
        response.setData(user);
        return response;
    }


}

