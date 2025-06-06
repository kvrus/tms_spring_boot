package ru.moscow.tms.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.moscow.tms.auth.controller.dto.UserDto;
import ru.moscow.tms.auth.models.Role;
import ru.moscow.tms.auth.models.UserEntity;
import ru.moscow.tms.auth.repository.RoleRepository;
import ru.moscow.tms.auth.repository.UserRepository;
import ru.moscow.tms.tms.models.TPlan;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final private UserRepository repository;
    final private RoleRepository roleRepository;
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return repository.findByUsername(username).orElseThrow(()-> new IllegalStateException("User not found"));
            }
        };
    }

    public Page<UserDto> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"))).map(u -> new UserDto(u.getUsername(), u.getId(), u.getRoles().stream().map(Role::getName).toList()));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void updateUser(UserDto user) {
        UserEntity entity = repository.findById(user.getId()).orElseThrow();
        entity.setUsername(user.getName());
        entity.setRoles(user.getRoles().stream().map(r -> roleRepository.findByName(r).orElseThrow()).toList());
        repository.save(entity);
    }
}
