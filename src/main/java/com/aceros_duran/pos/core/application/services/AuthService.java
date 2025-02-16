package com.aceros_duran.pos.core.application.services;

import com.aceros_duran.pos.application.config.exception.AppException;
import com.aceros_duran.pos.infrastructure.adapters.input.dto.request.LoginRequest;
import com.aceros_duran.pos.infrastructure.adapters.input.dto.request.RegisterRequest;
import com.aceros_duran.pos.infrastructure.adapters.input.dto.response.AuthResponse;
import com.aceros_duran.pos.infrastructure.adapters.output.persistence.entities.ERoleEntity;
import com.aceros_duran.pos.infrastructure.adapters.output.persistence.entities.RoleEntity;
import com.aceros_duran.pos.infrastructure.adapters.output.persistence.entities.UserEntity;
import com.aceros_duran.pos.infrastructure.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (!user.isEnabled()) {
            throw new AppException("User is inactive", HttpStatus.UNAUTHORIZED);
        }
        
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            String token = jwtService.getToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .build();
        }

        throw new AppException("Bad password", HttpStatus.BAD_REQUEST);

    }

    public AuthResponse register(RegisterRequest request) {

        RoleEntity role;
        role = new RoleEntity();

        role.setRole(ERoleEntity.valueOf(request.getRole()));

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        try {
            userRepository.save(user);

            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
        } catch (Exception e) {
            throw new AppException("User already exists", HttpStatus.CONFLICT, e);
        }

    }
}
