package com.example.template_PC.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.template_PC.common.exception.UnauthorizedException;
import com.example.template_PC.jwt.domain.JwtService;
import com.example.template_PC.jwt.dto.JwtAuthLoginDto;
import com.example.template_PC.jwt.dto.JwtAuthResponseDto;
import com.example.template_PC.user.domain.User;
import com.example.template_PC.user.domain.UserService;
import com.example.template_PC.user.dto.CreateUserDto;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthResponseDto jwtLogin(JwtAuthLoginDto loginDto) {
        User user = userService.getUserByUsername(loginDto.getUsername());

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public JwtAuthResponseDto jwtRegisterAdmin(CreateUserDto userDto) {
        User user = userService.createAdminUser(userDto);

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

}