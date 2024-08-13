package ru.moscow.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.moscow.tms.controller.dto.SignUpDto;
import ru.moscow.tms.models.Role;
import ru.moscow.tms.models.UserEntity;
import ru.moscow.tms.repository.RoleRepository;
import ru.moscow.tms.repository.UserRepository;

import java.util.Collections;

import static ru.moscow.tms.security.AuthUtil.USER_ROLE;

@Service
@RequiredArgsConstructor
public class AuthService {
    final private AuthenticationManager authenticationManager;
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private PasswordEncoder encoder;

    public UserEntity signUp(SignUpDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalStateException("User name is not allowed");
        }
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        Role roles = roleRepository.findByName(USER_ROLE).get();
        user.setRoles(Collections.singletonList(roles));
        return userRepository.save(user);
    }

}
