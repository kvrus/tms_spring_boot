package ru.moscow.tms.front.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class FrontUserModelAttribute {
    /*
    @ModelAttribute("currentUser")
    public Authentication populateUserFromSession() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
     */
}
