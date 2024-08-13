package ru.moscow.tms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.controller.dto.SignInDto;
import ru.moscow.tms.controller.dto.SignInResponseDto;
import ru.moscow.tms.controller.dto.SignUpDto;
import ru.moscow.tms.models.UserEntity;

import ru.moscow.tms.service.AuthService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    final private AuthService service;

    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignUpDto signup) {
        UserEntity user = service.signUp(signup);
        return new ResponseEntity<>("User created you may login now", HttpStatus.OK);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponseDto signInUser(@RequestBody SignInDto signIn) {
        SignInResponseDto user = service.signIn(signIn);
        return user;
    }
}
