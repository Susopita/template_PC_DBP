package com.example.template_PC.auth.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.template_PC.auth.domain.AuthService;
import com.example.template_PC.jwt.dto.JwtAuthLoginDto;
import com.example.template_PC.jwt.dto.JwtAuthResponseDto;
import com.example.template_PC.user.dto.CreateUserDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtAuthResponseDto login(@Valid @RequestBody JwtAuthLoginDto loginDto) {
        return authService.jwtLogin(loginDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthResponseDto register(@Valid @RequestBody CreateUserDto registerDto) {
        return authService.jwtRegisterAdmin(registerDto);
    }

}
