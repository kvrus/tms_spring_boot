package ru.moscow.tms.front.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.moscow.tms.auth.controller.dto.SignInDto;
import ru.moscow.tms.auth.controller.dto.SignInResponseDto;
import ru.moscow.tms.auth.controller.dto.SignUpDto;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.service.AuthService;

@Controller
@RequiredArgsConstructor
public class FrontAuthController {

    final private AuthService service;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        return "registration";
    }

    @ModelAttribute("user")
    public SignUpDto wrapFormToModel() {
        return new SignUpDto();
    }

    @PostMapping("/api/registration")
    public String registerNewUser(@ModelAttribute("user") SignUpDto signup) {
        UserEntity user = service.signUp(signup);
        return "redirect:/registration?success";
    }
/*
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponseDto signInUser(@RequestBody SignInDto signIn) {
        return service.signIn(signIn);
    }
*/
}
