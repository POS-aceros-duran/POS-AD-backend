package com.aceros_duran.pos.authenticationProvider.service;

import com.aceros_duran.pos.authenticationProvider.dtos.AuthResponse;
import com.aceros_duran.pos.authenticationProvider.dtos.LoginRequest;
import com.aceros_duran.pos.authenticationProvider.dtos.RegisterRequest;
import com.aceros_duran.pos.authenticationProvider.jwt.service.JwtService;
import com.aceros_duran.pos.authenticationProvider.model.ERole;
import com.aceros_duran.pos.authenticationProvider.model.RoleModel;
import com.aceros_duran.pos.authenticationProvider.model.UserModel;
import com.aceros_duran.pos.authenticationProvider.repositories.UserRepository;
import com.aceros_duran.pos.exception.AppException;
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
