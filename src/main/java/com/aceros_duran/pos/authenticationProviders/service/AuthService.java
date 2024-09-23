package com.aceros_duran.pos.authenticationProviders.service;

import com.aceros_duran.pos.authenticationProviders.dtos.AuthResponse;
import com.aceros_duran.pos.authenticationProviders.dtos.LoginRequest;
import com.aceros_duran.pos.authenticationProviders.dtos.RegisterRequest;
import com.aceros_duran.pos.authenticationProviders.jwt.service.JwtService;
import com.aceros_duran.pos.authenticationProviders.model.ERole;
import com.aceros_duran.pos.authenticationProviders.model.RoleModel;
import com.aceros_duran.pos.authenticationProviders.model.UserModel;
import com.aceros_duran.pos.authenticationProviders.repositories.UserRepository;
import com.aceros_duran.pos.exceptions.AppException;
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

        UserModel user = userRepository.findByUsername(request.getUsername())
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

        RoleModel role;
        role = new RoleModel();

        role.setRole(ERole.valueOf(request.getRole()));

        UserModel user = UserModel.builder()
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
