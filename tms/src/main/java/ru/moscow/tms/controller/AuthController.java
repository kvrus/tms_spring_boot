package ru.moscow.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.moscow.tms.controller.dto.SignUpDto;
import ru.moscow.tms.models.Role;
import ru.moscow.tms.models.UserEntity;
import ru.moscow.tms.repository.RoleRepository;
import ru.moscow.tms.repository.UserRepository;
import java.util.Collections;

import static ru.moscow.tms.security.RolesUtil.USER_ROLE;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    final private AuthenticationManager authenticationManager;
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private PasswordEncoder encoder;

    @PostMapping("signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignUpDto signup) {
        if (userRepository.existsByUsername(signup.getUsername())) {
            return new ResponseEntity<>("User name is not allowed", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setPassword(signup.getPassword());
        user.setUsername(encoder.encode(signup.getUsername()));
        Role roles = roleRepository.findByName(USER_ROLE).get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);

        return new ResponseEntity<>("User name is not allowed", HttpStatus.BAD_REQUEST);
    }
}
