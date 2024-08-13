package ru.moscow.tms.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.moscow.tms.models.UserEntity;
import ru.moscow.tms.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    final private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(repository.existsByUsername(username)) {
            UserEntity user = repository.findByUsername(username).orElseThrow( () -> new IllegalStateException("UsernameNotFound"));
            return new User(user.getUsername(), user.getPassword(), user.getRoles().stream().map( item -> new SimpleGrantedAuthority(item.getName())).toList());
        } else {
            throw new IllegalStateException("UsernameNotFound");
        }
    }
}
