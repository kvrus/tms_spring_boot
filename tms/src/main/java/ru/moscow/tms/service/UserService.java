package ru.moscow.tms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.moscow.tms.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository repository;
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return repository.findByUsername(username).orElseThrow(()-> new IllegalStateException("User not found"));
            }
        };
    }
}
