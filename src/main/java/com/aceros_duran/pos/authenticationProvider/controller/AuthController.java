package com.aceros_duran.pos.authenticationProvider.controller;

import com.aceros_duran.pos.authenticationProvider.dtos.ApiResponse;
import com.aceros_duran.pos.authenticationProvider.dtos.AuthResponse;
import com.aceros_duran.pos.authenticationProvider.dtos.LoginRequest;
import com.aceros_duran.pos.authenticationProvider.dtos.RegisterRequest;
import com.aceros_duran.pos.authenticationProvider.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pos-duran/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);

        Map<String,Object> objects = new HashMap<>();
        objects.put("token",authResponse.getToken());

        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .response(objects)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

}
