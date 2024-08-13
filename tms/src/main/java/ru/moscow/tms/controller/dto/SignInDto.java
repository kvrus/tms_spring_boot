package ru.moscow.tms.controller.dto;

import lombok.Data;

@Data
public class SignInDto {
    private String username;
    private String password;
}
