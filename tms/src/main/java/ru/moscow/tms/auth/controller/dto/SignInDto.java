package ru.moscow.tms.auth.controller.dto;

import lombok.Data;

@Data
public class SignInDto {
    private String username;
    private String password;
}
