package ru.moscow.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.moscow.tms.controller.dto.RefreshTokenDto;
import ru.moscow.tms.controller.dto.SignInDto;
import ru.moscow.tms.controller.dto.SignInResponseDto;
import ru.moscow.tms.controller.dto.SignUpDto;
import ru.moscow.tms.models.Role;
import ru.moscow.tms.models.UserEntity;
import ru.moscow.tms.repository.RoleRepository;
import ru.moscow.tms.repository.UserRepository;
import ru.moscow.tms.security.JwtService;

import java.util.Collections;
import java.util.HashMap;

import static ru.moscow.tms.security.AuthUtil.USER_ROLE;

@Service
@RequiredArgsConstructor
public class AuthService {
    final private AuthenticationManager authenticationManager;
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private PasswordEncoder encoder;
    final private JwtService jwtService;

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

    public SignInResponseDto signIn(SignInDto signin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getUsername(), signin.getPassword()));
        UserEntity user = userRepository.findByUsername(signin.getUsername()).get();
        String jwt = jwtService.generateToken(user);
        String jwtRefresh = jwtService.generateRefreshToken(new HashMap<>(), user);
        return new SignInResponseDto(jwt,jwtRefresh);
    }


    public SignInResponseDto refreshToken(RefreshTokenDto token) {
        String username = jwtService.extractUserName(token.getToken());
        UserEntity user = userRepository.findByUsername(username).get();
        if(jwtService.isTokenValid(token.getToken(), user)) {
            String jwt = jwtService.generateToken(user);
            return new SignInResponseDto(jwt, null);
        }
        return null;
    }
}
