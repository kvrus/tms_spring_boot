package ru.moscow.tms.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String hello() {
        return "Admin hello";
    }
}
