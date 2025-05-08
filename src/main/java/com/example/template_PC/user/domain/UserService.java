package com.example.template_PC.user.domain;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.template_PC.common.exception.ResourceConflictException;
import com.example.template_PC.user.dto.CreateUserDto;
import com.example.template_PC.user.infrastructure.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public Optional<User> getUserByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByUsername(String username) {
        return getUserByUsernameOptional(username).orElseThrow();
    }

    public User createAdminUser(CreateUserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ResourceConflictException("User with username already exists");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
