package com.authentication.controller;

import com.authentication.dto.APIResponse;
import com.authentication.dto.LoginDto;
import com.authentication.dto.UpdatePasswordDto;
import com.authentication.dto.UserDto;
import com.authentication.entity.User;
import com.authentication.repository.UserRepository;
import com.authentication.service.AuthService;
import com.authentication.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> register(
            @RequestBody UserDto userDto
            ){
        APIResponse<?> response = authService.register(userDto);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @PostMapping("/update-password")
    public ResponseEntity<APIResponse> updatePassword(
            @RequestBody UpdatePasswordDto updatePasswordDto
    ){
        APIResponse<?> response = authService.changePassword(updatePasswordDto);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> loginCheck(@RequestBody LoginDto loginDto){

        APIResponse<String> response = new APIResponse<>();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication authenticate = authManager.authenticate(token);

            if(authenticate.isAuthenticated()) {
                String jwtToken = jwtService.generateToken(loginDto.getUsername(),
                        authenticate.getAuthorities().iterator().next().getAuthority());

                response.setMessage("Login Successful");
                response.setStatus(200);
                response.setData(jwtToken);  // return JWT
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @GetMapping("/getUser")
    public User getUser(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

}
