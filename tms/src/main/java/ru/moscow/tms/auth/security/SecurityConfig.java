package ru.moscow.tms.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.moscow.tms.auth.service.UserService;

import static ru.moscow.tms.auth.security.AuthUtil.SUPERUSER_ROLE;
import static ru.moscow.tms.auth.security.AuthUtil.USER_ROLE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/topic/**","/ws","/app/**",
                                         "/api/auth/**",
                                         "/swagger-ui/**",
                                         "/v2/api-docs/**",
                                         "/css/**",
                                         "/js/**",
                                         "/images/**",
                                         "/v3/api-docs/**",
                                         "/swagger-ui/**",
                                         "/swagger-resources/**",
                                         "/v2/api-docs/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority(SUPERUSER_ROLE)
                        .requestMatchers("/api/admin/**").hasAuthority(SUPERUSER_ROLE)
                        .requestMatchers("/api/test/**").hasAnyAuthority(SUPERUSER_ROLE, USER_ROLE)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")          // Custom login page URL
                        .loginProcessingUrl("/login") // URL to submit username and password POST
                        .defaultSuccessUrl("/plans", true) // Redirect after login success
                        .failureUrl("/login?error")   // Redirect after login failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(service.userDetailsService())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
