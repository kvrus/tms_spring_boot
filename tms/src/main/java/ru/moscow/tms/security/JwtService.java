package ru.moscow.tms.security;

import org.springframework.security.core.userdetails.UserDetails;
import ru.moscow.tms.models.UserEntity;

import java.util.HashMap;
import java.util.Map;

public interface JwtService {

    String extractUserName(String token);
    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
