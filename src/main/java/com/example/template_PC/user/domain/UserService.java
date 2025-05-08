package com.example.template_PC.user.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.template_PC.user.infrastructure.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }
}
