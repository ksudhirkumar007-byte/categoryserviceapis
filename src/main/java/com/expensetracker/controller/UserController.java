package com.expensetracker.controller;

import com.expensetracker.dto.LoginResponse;
import com.expensetracker.dto.RefreshTokenRequest;
import com.expensetracker.dto.UserDTO;
import com.expensetracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://xpenss.in","http://localhost:3000"})
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public LoginResponse registerUser(@Valid @RequestBody UserDTO user){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashPassword = encoder.encode(user.getPasswordHash());
        LoginResponse loginResponse = userService.registerUser(user.getEmail(), hashPassword);
        return loginResponse;
    }

    @PostMapping("/login")
    public Record login(@Valid @RequestBody UserDTO userDTO){

        LoginResponse loginDetails = userService.checkLoginStatus(userDTO.getEmail(),userDTO.getPasswordHash());

        return loginDetails;
    }

    @PostMapping("/refresh")
    public Record refreshToken(@Valid @RequestBody RefreshTokenRequest refreshToken){
        System.out.println("refresh token is "+refreshToken);
        LoginResponse loginDetails = userService.refresh(refreshToken.refreshToken());
        return loginDetails;
    }
}
