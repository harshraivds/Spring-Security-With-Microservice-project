package com.authentication.service;

import com.authentication.dto.APIResponse;
import com.authentication.dto.UpdatePasswordDto;
import com.authentication.dto.UserDto;
import com.authentication.entity.User;
import com.authentication.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    public APIResponse<?> changePassword(UpdatePasswordDto updatePasswordDto) {

        APIResponse<Object> response = new APIResponse<>();

        // Check if username exists
        if (!userRepository.existsByUsername(updatePasswordDto.getUsername())) {
            response.setMessage("fail");
            response.setStatus(500);
            response.setData("Username already exists");
            return response;
        }

        // Check if email exists
        User user = userRepository.findByEmail(updatePasswordDto.getEmail());
        if (user == null) {
            response.setMessage("fail");
            response.setStatus(500);
            response.setData("Email already exists");
            return response;
        }

        // Check old password
        if (!passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword())) {
            response.setMessage("fail");
            response.setStatus(400);
            response.setData("Old password is incorrect");
            return response;
        }

        // Encrypt new password correctly
        String encodedNewPassword = passwordEncoder.encode(updatePasswordDto.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        response.setMessage("success");
        response.setStatus(200);
        response.setData("Password updated successfully");

        return response;
    }

}

